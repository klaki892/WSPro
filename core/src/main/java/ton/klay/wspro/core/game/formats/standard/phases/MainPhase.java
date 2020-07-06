package ton.klay.wspro.core.game.formats.standard.phases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.actions.PlayChoice;
import ton.klay.wspro.core.game.actions.PlayZonePair;
import ton.klay.wspro.core.game.cardLogic.ability.ActivatedAbility;
import ton.klay.wspro.core.game.cards.filters.CardFilter;
import ton.klay.wspro.core.game.cards.filters.CardTypeFilter;
import ton.klay.wspro.core.game.cards.filters.ColorFilter;
import ton.klay.wspro.core.game.cards.filters.LevelFilter;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.commands.Commands;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerCause;

import java.util.*;
import java.util.stream.Collectors;

import static ton.klay.wspro.core.api.scripting.cards.CardType.CHARACTER;
import static ton.klay.wspro.core.api.scripting.cards.CardType.EVENT;
import static ton.klay.wspro.core.game.actions.PlayChoiceAction.END_ACTION;
import static ton.klay.wspro.core.game.cards.filters.NumericFilter.Operation.EQUAL_TO;
import static ton.klay.wspro.core.game.cards.filters.NumericFilter.Operation.LESS_THAN_OR_EQUAL;

public class MainPhase extends BasePhase  {

    Logger log = LogManager.getLogger();
    private static final String invalidChoiceLogMessage = "Player Chose Choice not originally presented in Choice Timing( {} )";

    public MainPhase(PhaseHandler phaseHandler, GamePlayer turnPlayer) {
        super(phaseHandler, turnPlayer, TurnPhase.MAIN_PHASE);
    }

    @Override
    public void startPhase() {
        super.startPhase();
        doPlayTiming();
    }

    private void doPlayTiming() {

        //playtiming continues until player chooses to end it
        boolean endingPlayTiming = false;
        do {
            game.checkTiming();

            List<PlayChoice> playChoices = new ArrayList<>();

            //get all playable Actions
            playChoices.addAll(getPlayableCards());
            playChoices.addAll(getPlayableActAbilities());
//            playChoices.addAll(getExchangeableStagePositions());
            playChoices.add(PlayChoice.makeActionChoice(END_ACTION));

            PlayChoice choice = Commands.makeSinglePlayChoice(turnPlayer, playChoices);

            //determine what to do with the player's choice
            switch (choice.getChoiceType()){

                case CHOOSE_CARD:
                    if (!playCard(choice.getCard()))
                        logInvalidPlayChoice(choice);
                    break;
                case CHOOSE_ABILITY:
                    choice.getAbility().performEffect();
                    break;
                case EXCHANGE_POSITIONS:
                    exchangePositions(choice.getExchangeableStagePositions());
                    break;
                case CHOOSE_ACTION:
                    if (choice.getAction() != END_ACTION) {
                        logInvalidPlayChoice(choice);
                        continue;
                    }
                    endingPlayTiming = true;
                    break;
                default: //if they somehow choose a choice we dont want, ask again.
                    logInvalidPlayChoice(choice);
            }
        } while (!endingPlayTiming);
    }

    private List<PlayChoice> getPlayableCards() {
        /*  filter
            1. HAND
            2. meets current level requirements
            3. meet current color requirements (unless level 0)
            4. Character or Event
         */
        List<PlayingCard> handCards = turnPlayer.getPlayArea().getPlayZone(Zones.ZONE_HAND).getContents();

        PlayZone clock = turnPlayer.getPlayArea().getPlayZone(Zones.ZONE_CLOCK);
        PlayZone level = turnPlayer.getPlayArea().getPlayZone(Zones.ZONE_LEVEL);
        int playerLevel = level.size();

        //level 0 cards dont need color requirement
        CardFilter colorFilter = CardFilter.orFilter(ColorFilter.getPlayableColors(clock, level), new LevelFilter(EQUAL_TO, 0));

        CardFilter levelFilter = new LevelFilter(LESS_THAN_OR_EQUAL, playerLevel);

        CardFilter characterOrEventFilter = CardFilter
                .orFilter(new CardTypeFilter(CHARACTER), new CardTypeFilter(EVENT));
        CardFilter requirementFilter = CardFilter.andFilter(colorFilter, levelFilter);

        CardFilter playableCardFilter = CardFilter.andFilter(characterOrEventFilter, requirementFilter);

        List<PlayingCard> playableCards = playableCardFilter.filter(handCards);

        return playableCards.stream().map(PlayChoice::makeCardChoice).collect(Collectors.toList());

    }

    private List<PlayChoice> getPlayableActAbilities() {
        List<PlayingCard> stageCards = new ArrayList<>();
        for (PlayZone playZone : turnPlayer.getPlayArea().getPlayZones(Zones.ZONE_STAGE)) {
            stageCards.addAll(playZone.getContents());
        }

        List<PlayChoice> choices = new ArrayList<>();
        for (PlayingCard card : stageCards) {
            for (ActivatedAbility activatableAbility : card.getActivatableAbilities()) {
                if (activatableAbility.getCost().isPayable()){
                    choices.add(PlayChoice.makeAbilityChoice(activatableAbility));
                }
            }
        }

        return choices;
    }

    private boolean playCard(PlayingCard card) {
        PlayZone hand = turnPlayer.getPlayArea().getPlayZone(Zones.ZONE_HAND);

        if (card.getCardType() == CHARACTER){

            //ask for stage position
            List<PlayChoice> stageChoices = new ArrayList<>();
            turnPlayer.getPlayArea().getPlayZones(Zones.ZONE_STAGE).forEach(zone -> {
                stageChoices.add(PlayChoice.makeZoneChoice(zone));
            });

            PlayZone stagePosition = Commands.makeSinglePlayChoice(turnPlayer, stageChoices).getStagePosition();

            //All Choices resolved, pay cost and put on stage.
            Commands.payCost(card.getCost(), this);
            Commands.playCard(turnPlayer, card, hand, stagePosition, TriggerCause.GAME_ACTION, this);
        } else if (card.getCardType() == EVENT){

            //Events are played to resolution zone
            PlayZone resolution = turnPlayer.getPlayArea().getPlayZone(Zones.ZONE_RESOLUTION);

            //All Choices resolved, pay cost and put on stage.
            Commands.payCost(card.getCost(), this);
            Commands.playCard(turnPlayer, card, hand, resolution, TriggerCause.GAME_ACTION, this);
        }

        //somehow they picked a climax, return and log error message
        return false;
    }

    private void exchangePositions(PlayZonePair zonePair) {
        PlayZone firstZone = zonePair.getFirstZone();
        PlayZone secondZone = zonePair.getSecondZone();
        boolean firstZoneHasCards = firstZone.size() > 0;
        boolean secondZoneHasCards = secondZone.size() > 0;


        //if both zones have cards, we exchange them simultaneously
        if (firstZoneHasCards && secondZoneHasCards){
            game.enableSimultaneousLock();
        }

        //exchange the cards in the zones
        if (firstZoneHasCards){
            PlayingCard card = firstZone.getContents().get(0);
            Commands.moveCard(card, firstZone, secondZone,
                    Commands.Utilities.getTopOfZoneIndex(secondZone), card.getOrientation(), card.getVisibility(),
                    TriggerCause.GAME_ACTION, this);
        }
        if (secondZoneHasCards){
            PlayingCard card = secondZone.getContents().get(0);
            Commands.moveCard(card, secondZone, firstZone,
                    Commands.Utilities.getTopOfZoneIndex(firstZone), card.getOrientation(), card.getVisibility(),
                    TriggerCause.GAME_ACTION, this);
        }

        if (firstZoneHasCards && secondZoneHasCards){
            game.disableSimultaneousLock();
            game.interruptTiming();
            game.continuousTiming();
        }
    }

    private Collection<PlayChoice> getExchangeableStagePositions() {
        //each stage position is always exchangeable with every other one
        Collection<PlayZone> stage = turnPlayer.getPlayArea().getPlayZones(Zones.ZONE_STAGE);

        Set<PlayZonePair> choices = new HashSet<>();
        for (PlayZone stage1 : stage) {
            for (PlayZone stage2 : stage){
                if (stage1 != stage2){
                    //no point in exchanging empty fields
                    if (stage1.size() > 0 || stage2.size() > 0) {
                        choices.add(new PlayZonePair(stage1, stage2));
                    }
                }
            }
        }
        return choices.stream().map(PlayChoice::makeExchangePositionsChoice).collect(Collectors.toList());
    }


    private void logInvalidPlayChoice(PlayChoice choice){
        log.warn(invalidChoiceLogMessage, choice.getChoiceType());
    }

}

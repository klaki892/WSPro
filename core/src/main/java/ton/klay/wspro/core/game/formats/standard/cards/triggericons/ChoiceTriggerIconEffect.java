package ton.klay.wspro.core.game.formats.standard.cards.triggericons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.CardOrientation;
import ton.klay.wspro.core.api.cards.CardTrigger;
import ton.klay.wspro.core.api.cards.abilities.components.effects.Effect;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.actions.PlayChoice;
import ton.klay.wspro.core.game.actions.PlayChoiceAction;
import ton.klay.wspro.core.game.actions.PlayChoiceType;
import ton.klay.wspro.core.game.cards.CardType;
import ton.klay.wspro.core.game.cards.filters.CardFilter;
import ton.klay.wspro.core.game.cards.filters.CardTypeFilter;
import ton.klay.wspro.core.game.cards.filters.TriggerIconFilter;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.commands.Commands;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import ton.klay.wspro.core.game.formats.standard.zones.PlayZone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ChoiceTriggerIconEffect implements Effect {

    private static final Logger log = LogManager.getLogger();
    private final PlayingCard triggerCard;

    public ChoiceTriggerIconEffect(PlayingCard triggerCard) {

        this.triggerCard = triggerCard;
    }

    @Override
    public void execute(Object... vars) {
        GamePlayer player = getMaster();
        PlayZone waitingRoom = player.getPlayArea().getPlayZone(Zones.ZONE_WAITING_ROOM);
        PlayZone hand = player.getPlayArea().getPlayZone(Zones.ZONE_HAND);
        PlayZone stock = player.getPlayArea().getPlayZone(Zones.ZONE_STOCK);

        //get all character cards in waiting room with a stock icon
        CardTypeFilter characterFilter = new CardTypeFilter(CardType.CHARACTER);
        CardFilter soulIconFilter = new TriggerIconFilter(CardTrigger.SOUL);
        CardFilter cardFilter = CardFilter.andFilter(characterFilter, soulIconFilter);

        List<PlayChoice> choices = cardFilter.filter(waitingRoom.getContents())
                .stream().map(PlayChoice::makeCardChoice).collect(Collectors.toList());
        choices.add(PlayChoice.makeActionChoice(PlayChoiceAction.END_ACTION));

        PlayChoice cardChoice = Commands.makeSinglePlayChoice(player, choices);
        if (cardChoice.getChoiceType() != PlayChoiceType.CHOOSE_ACTION){

            //ask the player if they want to move it to stock or hand

            PlayChoice stockChoice = PlayChoice.makeZoneChoice(hand);
            PlayChoice handChoice = PlayChoice.makeZoneChoice(stock);

            PlayZone destinationZone = Commands.makeSinglePlayChoice(player, Arrays.asList(stockChoice, handChoice)).getZone();


            CardOrientation destinationOrientation;
            if (destinationZone.getZoneName() == Zones.ZONE_HAND) {
                destinationOrientation = CardOrientation.STAND;
            } else {
                destinationOrientation = CardOrientation.REST;
            }

            //move the card to the chosen zone
            PlayingCard chosenCard = cardChoice.getCard();
            Commands.moveCard(chosenCard, waitingRoom,
                    destinationZone, Commands.Utilities.getTopOfZoneIndex(destinationZone), destinationOrientation,
                    destinationZone.getVisibility(), TriggerCause.GAME_ACTION, this);
        }


        //todo announce effect?
    }

    @Override
    public GamePlayer getMaster() {
        return triggerCard.getMaster();
    }
}

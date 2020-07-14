package to.klay.wspro.core.game.formats.standard.cards.triggericons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.CardOrientation;
import to.klay.wspro.core.api.cards.GameVisibility;
import to.klay.wspro.core.api.cards.abilities.components.effects.Effect;
import to.klay.wspro.core.api.game.field.Zones;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.actions.PlayChoice;
import to.klay.wspro.core.game.actions.PlayChoiceAction;
import to.klay.wspro.core.game.actions.PlayChoiceType;
import to.klay.wspro.core.game.cards.CardType;
import to.klay.wspro.core.game.cards.filters.CardFilter;
import to.klay.wspro.core.game.cards.filters.CardTypeFilter;
import to.klay.wspro.core.game.cards.filters.LevelFilter;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.commands.Commands;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import to.klay.wspro.core.game.formats.standard.zones.PlayZone;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static to.klay.wspro.core.game.cards.filters.NumericFilter.Operation.LESS_THAN_OR_EQUAL;

public class StandbyTriggerIconEffect implements Effect {

    private static final Logger log = LogManager.getLogger();
    private final PlayingCard triggerCard;

    private StandbyTriggerIconEffect(PlayingCard triggerCard) {

        this.triggerCard = triggerCard;
    }

    public static StandbyTriggerIconEffect execute(PlayingCard triggerCard) {
        return new StandbyTriggerIconEffect(triggerCard);
    }

    @Override
    public void execute(Object... vars) {
        GamePlayer player = getMaster();
        PlayZone waitingRoom = player.getPlayArea().getPlayZone(Zones.ZONE_WAITING_ROOM);
        PlayZone hand = player.getPlayArea().getPlayZone(Zones.ZONE_HAND);
        int level = player.getPlayArea().getPlayZone(Zones.ZONE_LEVEL).size();

        //get all character cards in waiting room that are our level +1 and below
        CardTypeFilter characterFilter = new CardTypeFilter(CardType.CHARACTER);
        LevelFilter levelFilter = new LevelFilter(LESS_THAN_OR_EQUAL, level+1);
        CardFilter cardFilter = CardFilter.andFilter(characterFilter, levelFilter);

        //select card from waiting room
        List<PlayChoice> waitingRoomChoices = cardFilter.filter(waitingRoom.getContents())
                .stream().map(PlayChoice::makeCardChoice).collect(Collectors.toList());
        waitingRoomChoices.add(PlayChoice.makeActionChoice(PlayChoiceAction.END_ACTION));

        PlayChoice waitingRoomChoice = Commands.makeSinglePlayChoice(player, waitingRoomChoices);

        if (waitingRoomChoice.getChoiceType() != PlayChoiceType.CHOOSE_ACTION){

            //ask for stage position
            List<PlayChoice> stageChoices = new ArrayList<>();
            player.getPlayArea().getPlayZones(Zones.ZONE_STAGE).forEach(zone -> {
                stageChoices.add(PlayChoice.makeZoneChoice(zone));
            });
            PlayZone stagePosition = Commands.makeSinglePlayChoice(player, stageChoices).getZone();


            //move the card to a stage position
            PlayingCard chosenCard = waitingRoomChoice.getCard();
            Commands.moveCard(chosenCard, waitingRoom,
                    stagePosition, Commands.Utilities.getTopOfZoneIndex(stagePosition), CardOrientation.REST,
                    GameVisibility.VISIBLE_TO_ALL, TriggerCause.GAME_ACTION, this);
        }


        //todo announce effect?
    }

    @Override
    public GamePlayer getMaster() {
        return triggerCard.getMaster();
    }
}

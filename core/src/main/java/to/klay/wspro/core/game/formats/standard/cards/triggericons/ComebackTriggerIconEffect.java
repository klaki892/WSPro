package to.klay.wspro.core.game.formats.standard.cards.triggericons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.CardOrientation;
import to.klay.wspro.core.api.cards.abilities.components.effects.Effect;
import to.klay.wspro.core.api.game.field.Zones;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.actions.PlayChoice;
import to.klay.wspro.core.game.actions.PlayChoiceAction;
import to.klay.wspro.core.game.actions.PlayChoiceType;
import to.klay.wspro.core.game.cards.CardType;
import to.klay.wspro.core.game.cards.filters.CardTypeFilter;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.commands.Commands;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import to.klay.wspro.core.game.formats.standard.zones.PlayZone;

import java.util.List;
import java.util.stream.Collectors;

public class ComebackTriggerIconEffect implements Effect {

    private static final Logger log = LogManager.getLogger();
    private final PlayingCard triggerCard;

    public ComebackTriggerIconEffect(PlayingCard triggerCard) {

        this.triggerCard = triggerCard;
    }

    @Override
    public void execute(Object... vars) {
        GamePlayer player = getMaster();
        PlayZone waitingRoom = player.getPlayArea().getPlayZone(Zones.ZONE_WAITING_ROOM);
        PlayZone hand = player.getPlayArea().getPlayZone(Zones.ZONE_HAND);

        //get all character cards in waiting room
        CardTypeFilter characterFilter = new CardTypeFilter(CardType.CHARACTER);
        List<PlayChoice> choices = characterFilter.filter(waitingRoom.getContents())
                .stream().map(PlayChoice::makeCardChoice).collect(Collectors.toList());
        choices.add(PlayChoice.makeActionChoice(PlayChoiceAction.END_ACTION));

        PlayChoice choice = Commands.makeSinglePlayChoice(player, choices);
        if (choice.getChoiceType() != PlayChoiceType.CHOOSE_ACTION){
            //bounce the card back to hand
            PlayingCard chosenCard = choice.getCard();
            Commands.moveCard(chosenCard, waitingRoom,
                    hand, Commands.Utilities.getTopOfZoneIndex(hand), CardOrientation.STAND,
                    hand.getVisibility(), TriggerCause.GAME_ACTION, this);
        }


        //todo announce effect?
    }

    @Override
    public GamePlayer getMaster() {
        return triggerCard.getMaster();
    }
}

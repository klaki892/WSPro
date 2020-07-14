package ton.klay.wspro.core.game.events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.CardOrientation;
import ton.klay.wspro.core.api.game.GameRuntimeException;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.commands.Commands;
import ton.klay.wspro.core.game.formats.standard.triggers.RefreshPointAddedTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import ton.klay.wspro.core.game.formats.standard.zones.PlayZone;

public class ReshuffleRuleAction extends InterruptRuleAction {

    private static final Logger log = LogManager.getLogger();

    private final PlayZone deck = player.getPlayArea().getPlayZone(Zones.ZONE_DECK);
    private final PlayZone waitingRoom = player.getPlayArea().getPlayZone(Zones.ZONE_WAITING_ROOM);
    private final PlayZone resolution = player.getPlayArea().getPlayZone(Zones.ZONE_RESOLUTION);


    public ReshuffleRuleAction(GamePlayer player){
        super(player);
    }

    @Override
    public void execute() {
        if (waitingRoom.size() == 0  ){
            //FIXME if in Damage process &  no climax in resolution, Immediate game lost
            // if not in damage process, create temporary listener for cards to be back in waiting room,
            // then reissue reshuffle
            throw new GameRuntimeException("Empty zones during reshuffle not implemented");
        }

        //move waiting room into deck
        for (PlayingCard card : waitingRoom.getContents()) {
            Commands.moveCard(card, waitingRoom, deck, 0, CardOrientation.STAND, deck.getVisibility(),
                    TriggerCause.GAME_ACTION, this);
        }

        Commands.shuffleZone(deck, TriggerCause.GAME_ACTION, this);
        RefreshPointAddedTrigger trigger = new RefreshPointAddedTrigger(player, TriggerCause.GAME_ACTION, this);
        game.getTriggerManager().post(trigger);
        game.continuousTiming();
    }

}

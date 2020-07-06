package ton.klay.wspro.core.game.events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.CardOrientation;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.commands.Commands;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerCause;

/**
 * Handles the event where multiple characters are on the same stage position
 */
public class MultipleClimaxRuleAction implements RuleAction{

    private static final Logger log = LogManager.getLogger();
    private final GamePlayer player;

    public MultipleClimaxRuleAction(GamePlayer player) {

        this.player = player;
    }

    @Override
    public void execute() {
        //todo how do we handle the simultaneous case?

        PlayZone waiting = player.getPlayArea().getPlayZone(Zones.ZONE_WAITING_ROOM);
        PlayZone climaxZone = player.getPlayArea().getPlayZone(Zones.ZONE_CLIMAX);
        if (climaxZone.getContents().size() > 1){
            int allButTopAmount = climaxZone.getContents().size()-1;

            //put all but the last card to enter the stage into the waiting room
            for (PlayingCard card : Commands.Utilities.getBottomOfZoneCards(climaxZone, allButTopAmount)) {
                Commands.moveCard(card, climaxZone, waiting, Commands.Utilities.getTopOfZoneIndex(waiting),
                        CardOrientation.STAND, waiting.getVisibility(), TriggerCause.GAME_ACTION, player);
            }
        }
    }
}

package ton.klay.wspro.core.game.events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.CardOrientation;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.commands.Commands;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import ton.klay.wspro.core.game.formats.standard.zones.PlayZone;

import java.util.Collection;

/**
 * Handles the event where multiple characters are on the same stage position
 */
public class MultipleCharacterRuleAction implements RuleAction{

    private static final Logger log = LogManager.getLogger();
    private final GamePlayer player;

    public MultipleCharacterRuleAction(GamePlayer player) {

        this.player = player;
    }

    @Override
    public void execute() {
        //todo how do we handle the simultaneous case?

        PlayZone waiting = player.getPlayArea().getPlayZone(Zones.ZONE_WAITING_ROOM);
        Collection<PlayZone> stageZones = player.getPlayArea().getPlayZones(Zones.ZONE_STAGE);
        for (PlayZone stageZone : stageZones) {
            if (stageZone.getContents().size() > 1){
                int allButTopAmount = stageZone.getContents().size()-1;

                //put all but the last card to enter the stage into the waiting room
                for (PlayingCard card : Commands.Utilities.getBottomOfZoneCards(stageZone, allButTopAmount)) {
                    Commands.moveCard(card, stageZone, waiting, Commands.Utilities.getTopOfZoneIndex(waiting),
                            CardOrientation.STAND, waiting.getVisibility(), TriggerCause.GAME_ACTION, player);
                }
            }
        }

    }
}

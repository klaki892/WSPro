package ton.klay.wspro.core.game.formats.standard.triggers.listeners;

import com.google.common.eventbus.Subscribe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.game.Game;
import ton.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.CardMovedTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.ClockFullTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerCause;

/**
 * Observes a game and issues a Trigger Condition whenver a player's clock has 7 or more cards.
 */
public class ClockFullIssuer extends BaseObserver {

    private static final Logger log = LogManager.getLogger();

    public ClockFullIssuer(Game game){
        super(game);
    }

    @Subscribe
    public void triggerReceived(CardMovedTrigger event) {
        if (event.getDestinationZone().getZoneName() == Zones.ZONE_CLOCK){
            if (event.getDestinationZone().size() >= 7 ){
                BaseTrigger trigger = new ClockFullTrigger(event.getDestinationZone().getMaster(),
                        TriggerCause.GAME_ACTION, event.getSourceZone());
                game.getTriggerManager().post(trigger);
                game.continuousTiming();
            }
        }
    }
}

package to.klay.wspro.core.game.formats.standard.triggers.listeners;

import com.google.common.eventbus.Subscribe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.game.field.Zones;
import to.klay.wspro.core.game.Game;
import to.klay.wspro.core.game.formats.standard.triggers.CardMovedTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.DeckEmptyTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;

/**
 * Observes a game and issues a Trigger Condition whenver a player's deck has no cards.
 */
public class DeckEmptyIssuer extends BaseObserver {

    private static final Logger log = LogManager.getLogger();

    public DeckEmptyIssuer(Game game){
        super(game);
    }

    @Subscribe
    public void triggerReceived(CardMovedTrigger event) {
        if (event.getSourceZone().getZoneName() == Zones.ZONE_DECK){
            if (event.getSourceZone().size() == 0){
                DeckEmptyTrigger trigger = new DeckEmptyTrigger(event.getSourceZone().getMaster(),
                        TriggerCause.GAME_ACTION, event.getSourceZone());
                game.getTriggerManager().post(trigger);
                game.continuousTiming();
            }
        }
    }
}

package ton.klay.wspro.core.game.formats.standard.triggers.listeners.ruleactions;

import com.google.common.eventbus.Subscribe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.game.Game;
import ton.klay.wspro.core.game.events.ReshuffleRuleAction;
import ton.klay.wspro.core.game.formats.standard.triggers.DeckEmptyTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.listeners.BaseObserver;

/**
 * Observes a game and issues a {@link DeckEmptyTrigger} if a player's deck is empty
 */
public class ReshuffleRuleIssuer extends BaseObserver {

    private static final Logger log = LogManager.getLogger();

    public ReshuffleRuleIssuer(Game game){
        super(game);
    }

    @Subscribe
    public void triggerReceived(DeckEmptyTrigger event) {
        game.getTimingManager().add(new ReshuffleRuleAction(event.getPlayer()));
    }
}

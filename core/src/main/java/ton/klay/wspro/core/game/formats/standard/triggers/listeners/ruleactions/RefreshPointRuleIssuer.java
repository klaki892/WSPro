package ton.klay.wspro.core.game.formats.standard.triggers.listeners.ruleactions;

import com.google.common.eventbus.Subscribe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.game.Game;
import ton.klay.wspro.core.game.events.RefreshPointRuleAction;
import ton.klay.wspro.core.game.formats.standard.triggers.RefreshPointAddedTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.listeners.BaseObserver;

/**
 * Observes a game and issues a {@link RefreshPointAddedTrigger} if a player's deck is empty
 */
public class RefreshPointRuleIssuer extends BaseObserver {

    private static final Logger log = LogManager.getLogger();

    public RefreshPointRuleIssuer(Game game) {
        super(game);
    }

    @Subscribe
    public void triggerReceived(RefreshPointAddedTrigger trigger) {
        game.getTimingManager().add(new RefreshPointRuleAction(trigger.getPlayer()));

    }
}

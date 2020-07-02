package ton.klay.wspro.core.game.formats.standard.triggers.listeners.ruleactions;

import com.google.common.eventbus.Subscribe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.game.Game;
import ton.klay.wspro.core.game.events.LevelUpRuleAction;
import ton.klay.wspro.core.game.formats.standard.triggers.ClockFullTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.listeners.BaseObserver;

/**
 * Observes a game and issues a {@link LevelUpRuleAction} if a player's deck is empty
 */
public class LevelUpRuleIssuer extends BaseObserver {

    private static final Logger log = LogManager.getLogger();

    public LevelUpRuleIssuer(Game game){
        super(game);
    }

    @Subscribe
    public void triggerReceived(ClockFullTrigger event) {
        game.getTimingManager().add(new LevelUpRuleAction(event.getPlayer()));
    }
}

package ton.klay.wspro.core.game.formats.standard.triggers.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.game.Game;
import ton.klay.wspro.core.game.events.RefreshPointRuleAction;
import ton.klay.wspro.core.game.formats.standard.triggers.RefreshPointAddedTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerObserver;

public class RefreshPointIssuer extends TriggerObserver<RefreshPointAddedTrigger> {

    private static final Logger log = LogManager.getLogger();

    public RefreshPointIssuer(Game game) {
        super(game);
    }

    @Override
    public void triggerReceived(RefreshPointAddedTrigger trigger) {
        game.getTimingManager().add(new RefreshPointRuleAction(trigger.getPlayer()));

    }
}

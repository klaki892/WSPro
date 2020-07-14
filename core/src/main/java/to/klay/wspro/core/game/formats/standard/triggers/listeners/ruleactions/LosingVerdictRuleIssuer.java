package to.klay.wspro.core.game.formats.standard.triggers.listeners.ruleactions;

import com.google.common.eventbus.Subscribe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.game.LoseConditions;
import to.klay.wspro.core.api.game.field.Zones;
import to.klay.wspro.core.game.Game;
import to.klay.wspro.core.game.events.LosingVerdictRuleAction;
import to.klay.wspro.core.game.formats.standard.triggers.LeveledUpTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.listeners.BaseObserver;

/**
 * Observes a game and issues a {@link LosingVerdictRuleAction} if a player's deck is empty
 */
public class LosingVerdictRuleIssuer extends BaseObserver {

    private static final Logger log = LogManager.getLogger();

    public LosingVerdictRuleIssuer(Game game){
        super(game);
    }

    @Subscribe
    public void triggerReceived(LeveledUpTrigger event) {
        if (event.getPlayer().getPlayArea().getPlayZone(Zones.ZONE_LEVEL).size() >= 4)
        game.getTimingManager().add(new LosingVerdictRuleAction(event.getPlayer(), LoseConditions.LEVEL4));
    }
}

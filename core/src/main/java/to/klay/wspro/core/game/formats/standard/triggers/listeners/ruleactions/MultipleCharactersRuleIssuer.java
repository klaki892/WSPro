package to.klay.wspro.core.game.formats.standard.triggers.listeners.ruleactions;

import com.google.common.eventbus.Subscribe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.game.field.Zones;
import to.klay.wspro.core.game.Game;
import to.klay.wspro.core.game.events.MultipleCharacterRuleAction;
import to.klay.wspro.core.game.formats.standard.triggers.CardMovedTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.listeners.BaseObserver;
import to.klay.wspro.core.game.formats.standard.zones.PlayZone;

/**
 * Observes a game and issues a {@link MultipleCharacterRuleAction} if a stage zone has more than 1 card on it.
 */
public class MultipleCharactersRuleIssuer extends BaseObserver {

    private static final Logger log = LogManager.getLogger();

    public MultipleCharactersRuleIssuer(Game game){
        super(game);
    }

    @Subscribe
    public void triggerReceived(CardMovedTrigger event) {
        PlayZone destinationZone = event.getDestinationZone();
        if (Zones.isOnStage(destinationZone) && destinationZone.getContents().size() > 1){
            game.getTimingManager().add(new MultipleCharacterRuleAction(destinationZone.getMaster()));
        }
    }
}
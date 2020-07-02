package ton.klay.wspro.core.game.formats.standard.triggers.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.game.Game;
import ton.klay.wspro.core.game.events.ReshuffleRuleAction;
import ton.klay.wspro.core.game.formats.standard.triggers.DeckEmptyTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerObserver;

/**
 * Observes a game and issues a {@link ReshuffleRuleAction} if a player's deck is empty
 */
public class ReshuffleIssuer extends TriggerObserver<DeckEmptyTrigger> {

    private static final Logger log = LogManager.getLogger();

    public ReshuffleIssuer(Game game){
        super(game);
    }

    @Override
    public void triggerReceived(DeckEmptyTrigger event) {
        game.getTimingManager().add(new ReshuffleRuleAction(event.getPlayer()));
    }
}

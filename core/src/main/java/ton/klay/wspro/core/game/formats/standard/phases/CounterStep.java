package ton.klay.wspro.core.game.formats.standard.phases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.player.GamePlayer;

public class CounterStep extends BasePhase  {

    Logger log = LogManager.getLogger();

    public CounterStep(PhaseHandler phaseHandler, GamePlayer turnPlayer) {
        super(phaseHandler, turnPlayer, TurnPhase.COUNTER_STEP);
    }

    @Override
    public void startPhase() {
        super.startPhase();
        /* TODO:
            3.	Opponent Play timing
        */
        game.checkTiming();
    }

}

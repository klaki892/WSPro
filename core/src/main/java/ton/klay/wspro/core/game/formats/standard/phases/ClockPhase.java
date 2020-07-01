package ton.klay.wspro.core.game.formats.standard.phases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.player.GamePlayer;

public class ClockPhase extends BasePhase  {

    Logger log = LogManager.getLogger();

    public ClockPhase(PhaseHandler phaseHandler, GamePlayer turnPlayer) {
        super(phaseHandler, turnPlayer, TurnPhase.CLOCK_PHASE);
    }

    @Override
    public void startPhase() {
        super.startPhase();

        /* TODO
            3.	Playtiming: may clock & draw 2
            	 (Strictly speaking: not a play timing, yet meets all conditions of such)
        */
        game.checkTiming();
    }

}

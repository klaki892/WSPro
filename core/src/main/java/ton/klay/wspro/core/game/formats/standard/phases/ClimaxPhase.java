package ton.klay.wspro.core.game.formats.standard.phases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.player.GamePlayer;

public class ClimaxPhase extends BasePhase  {

    Logger log = LogManager.getLogger();

    public ClimaxPhase(PhaseHandler phaseHandler, GamePlayer turnPlayer) {
        super(phaseHandler, turnPlayer, TurnPhase.CLIMAX_PHASE);
    }

    @Override
    public void startPhase() {
        super.startPhase();

        /* TODO
                3.	Play timing
         */
        game.checkTiming();
    }

}

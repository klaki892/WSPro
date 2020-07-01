package ton.klay.wspro.core.game.formats.standard.phases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.player.GamePlayer;

public class MainPhase extends BasePhase  {

    Logger log = LogManager.getLogger();

    public MainPhase(PhaseHandler phaseHandler, GamePlayer turnPlayer) {
        super(phaseHandler, turnPlayer, TurnPhase.MAIN_PHASE);
    }

    @Override
    public void startPhase() {
        super.startPhase();

        /* TODO
            2.	Repeat until no choice is made:
            	Check timing
            	Play timing

         */
    }

}

package ton.klay.wspro.core.game.formats.standard.phases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.player.GamePlayer;

public class EndPhase extends BasePhase  {

    Logger log = LogManager.getLogger();

    public EndPhase(PhaseHandler phaseHandler, GamePlayer turnPlayer) {
        super(phaseHandler, turnPlayer, TurnPhase.END_PHASE);
    }

    @Override
    public void startPhase() {
        super.startPhase();
        /* TODO:
            3.	Hand size rule Action
            4.	Puts climax away Rule Action
            5.	Check timing
                	All effects that are “during turn” & until “end of turn” invalidate
            6.	Repeat: Check timing for 3 & 5 again, while something occurs
            7.	Advance phase (change current turn player)
        */
        phaseHandler.setCurrentTurnPlayer(phaseHandler.getNextTurnPlayer());
    }

}

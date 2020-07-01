package ton.klay.wspro.core.game.formats.standard.phases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.player.GamePlayer;

public class EncoreStep extends BasePhase  {

    Logger log = LogManager.getLogger();

    public EncoreStep(PhaseHandler phaseHandler, GamePlayer turnPlayer) {
        super(phaseHandler, turnPlayer, TurnPhase.ENCORE_STEP);
    }

    @Override
    public void startPhase() {
        super.startPhase();
        /* TODO:
            3.	Turn player Repeat (for all owned reversed characters):
                	Choose character, put into waiting room
                	Check timing
            4.	Non-turn player performs all of #3 for 1 character, then go back to step 3
            5.	Check timing
            6.	Go back to Step 3 if reversed characters on any player stage
            7.	Advance to end phase
        */
    }

}

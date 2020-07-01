package ton.klay.wspro.core.game.formats.standard.phases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.player.GamePlayer;

public class DamageStep extends BasePhase  {

    Logger log = LogManager.getLogger();

    public DamageStep(PhaseHandler phaseHandler, GamePlayer turnPlayer) {
        super(phaseHandler, turnPlayer, TurnPhase.DAMAGE_STEP);
    }

    @Override
    public void startPhase() {
        super.startPhase();
        /* TODO:
            3.	Deal damage (if attacking character exists and soul > 0)
            4.	Check timing
            5.	Advance to battle step if front attack
            6.	Else trigger condition fulfilled: end of attack
            7.	Advance to Attack Declaration Step
        */
    }

}

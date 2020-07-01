package ton.klay.wspro.core.game.formats.standard.phases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.player.GamePlayer;

public class TriggerStep extends BasePhase  {

    Logger log = LogManager.getLogger();

    public TriggerStep(PhaseHandler phaseHandler, GamePlayer turnPlayer) {
        super(phaseHandler, turnPlayer, TurnPhase.TRIGGER_STEP);
    }

    @Override
    public void startPhase() {
        super.startPhase();
        /* TODO:
            3.	Trigger check
                	All Trigger actions executed
                	Trigger card placed to stock
            4.	Check timing
            5.	Advance to counter step if frontal, damage step elsewise
        */
    }

}

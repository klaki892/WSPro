package ton.klay.wspro.core.game.formats.standard.phases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.player.GamePlayer;

public class AttackDeclarationStep extends BasePhase  {

    Logger log = LogManager.getLogger();

    public AttackDeclarationStep(PhaseHandler phaseHandler, GamePlayer turnPlayer) {
        super(phaseHandler, turnPlayer, TurnPhase.ATTACK_DECLARATION_STEP);
    }

    @Override
    public void startPhase() {
        super.startPhase();
        /* TODO:
            3.	Choose Attacker and type of attack and assign battle states  or advance to encore step (not a playtiming)
                4.	if chose attacker: Check timing
                5.	If No attacker: advance phase
         */
        //if no more choices, attack phase is over
        phaseHandler.setNextPhase(TurnPhase.ENCORE_STEP);
    }

}

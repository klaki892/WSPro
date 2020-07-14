package to.klay.wspro.core.game.formats.standard.phases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.game.player.GamePlayer;

public class AttackPhase extends BasePhase  {

    Logger log = LogManager.getLogger();

    public AttackPhase(PhaseHandler phaseHandler, GamePlayer turnPlayer) {
        super(phaseHandler, turnPlayer, TurnPhase.ATTACK_PHASE);
    }

    @Override
    public void startPhase() {
        super.startPhase();
        game.checkTiming();
        //attack phase is actually done in AttackDeclarationStep
    }

}

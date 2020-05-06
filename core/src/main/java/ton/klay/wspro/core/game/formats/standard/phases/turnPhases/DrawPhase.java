package ton.klay.wspro.core.game.formats.standard.phases.turnPhases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.phase.GamePhase;
import ton.klay.wspro.core.api.game.phase.TurnPhase;
import ton.klay.wspro.core.game.Duel;
import ton.klay.wspro.core.api.cards.abilities.components.AbilityConditions;

public class DrawPhase extends Phase {

    private static final Logger log = LogManager.getLogger();



    public DrawPhase(Duel duel) {
        super(duel, TurnPhase.TURN_PHASE_DRAW);
    }


    /**
     * Preform the Draw Phase
     */
    @Override
    public void startPhase() {
        log.debug("Starting Draw Phase.");
        duel.triggerCheck(AbilityConditions.CONDITION_START_DRAW_PHASE);
        duel.checkTiming();
        duel.getScriptingFunctions().drawCard(duel.getCurrentTurnPlayer());
        duel.checkTiming();
    }

    @Override
    public GamePhase nextPhase() {
        return null;
    }


}

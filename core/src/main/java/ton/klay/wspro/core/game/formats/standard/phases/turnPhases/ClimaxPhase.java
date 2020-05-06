package ton.klay.wspro.core.game.formats.standard.phases.turnPhases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.phase.GamePhase;
import ton.klay.wspro.core.api.game.phase.TurnPhase;
import ton.klay.wspro.core.game.Duel;
import ton.klay.wspro.core.api.cards.abilities.components.AbilityConditions;

public class ClimaxPhase extends Phase {

    private static final Logger log = LogManager.getLogger();


    public ClimaxPhase(Duel duel) {
        super(duel, TurnPhase.TURN_PHASE_CLIMAX);
    }

    /**
     * Preform the Climax Phase
     */
    @Override
    public void startPhase() {
        log.debug("Starting Climax Phase");
        duel.triggerCheck(AbilityConditions.CONDITION_START_CLIMAX_PHASE);
        duel.checkTiming();
        //action play climax code if user wants to
        duel.checkTiming();
    }

    @Override
    public GamePhase nextPhase() {
        //todo
        return null;
    }

}

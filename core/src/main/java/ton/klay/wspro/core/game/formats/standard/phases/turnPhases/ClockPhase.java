package ton.klay.wspro.core.game.formats.standard.phases.turnPhases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.phase.GamePhase;
import ton.klay.wspro.core.api.game.phase.TurnPhase;
import ton.klay.wspro.core.game.Duel;
import ton.klay.wspro.core.api.cards.abilities.components.AbilityConditions;


public class ClockPhase extends Phase {

    Logger log = LogManager.getLogger();

    public ClockPhase(Duel duel) {
        super(duel, TurnPhase.TURN_PHASE_CLOCK);
    }

    /**
     * Preform the Clock Phase
     */
    @Override
    public void startPhase() {
        //todo: preform logs of the actions in the clock phase
        log.debug("Starting Clock Phase");
        duel.triggerCheck(AbilityConditions.CONDITION_START_CLOCK_PHASE);
        duel.checkTiming();
        //todo: Action preform a clock if the user wants to
        duel.triggerCheck(AbilityConditions.CONDITION_ON_CLOCK);
        duel.checkTiming();

    }

    @Override
    public GamePhase nextPhase() {
        return null;
    }

}

package ton.klay.wspro.core.game.formats.standard.phases.turnPhases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.phase.GamePhase;
import ton.klay.wspro.core.api.game.phase.TurnPhase;
import ton.klay.wspro.core.game.Duel;
import ton.klay.wspro.core.api.cards.abilities.components.AbilityConditions;

public class EndPhase extends Phase {

    private static final Logger log = LogManager.getLogger();


    public EndPhase(Duel duel) {
        super(duel, TurnPhase.TURN_PHASE_END);
    }

    /**
     * Preform the End Phase
     */
    @Override
    public void startPhase() {
        log.debug("Beginning End Phase");
        duel.triggerCheck(AbilityConditions.CONDITION_START_END_PHASE);
        duel.checkTiming();
        handSizeLimit();
        removeClimax();

        log.debug("Ending Turn");
        duel.triggerCheck(AbilityConditions.CONDITION_END_OF_TURN);
        duel.checkTiming();

    }

    @Override
    public GamePhase nextPhase() {
        return null;
    }

    private void removeClimax() {
        //todo: action for removing a climax at the end of the turn
    }

    private void handSizeLimit() {
        //preforms the actions for checking for handsize limit of 7, give a rule action if it's greater
        //todo: code hand size limit action
    }

}

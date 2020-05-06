package ton.klay.wspro.core.game.formats.standard.phases.turnPhases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.phase.GamePhase;
import ton.klay.wspro.core.api.game.phase.TurnPhase;
import ton.klay.wspro.core.game.Duel;
import ton.klay.wspro.core.api.cards.abilities.components.AbilityConditions;
import ton.klay.wspro.core.game.enums.PlayTiming;

public class MainPhase extends Phase {

    private static final Logger log = LogManager.getLogger();


    public MainPhase(Duel duel) {
        super(duel, TurnPhase.TURN_PHASE_MAIN);
    }

    /**
     * Preform the Main Phase
     */
    @Override
    public void startPhase() {
        log.debug("Starting Main Phase");
        duel.triggerCheck(AbilityConditions.CONDITION_START_MAIN_PHASE);
        duel.checkTiming();
        duel.playTiming(PlayTiming.PLAY_TIMING_MAIN);
    }

    @Override
    public GamePhase nextPhase() {
        //todo
        return null;
    }

}

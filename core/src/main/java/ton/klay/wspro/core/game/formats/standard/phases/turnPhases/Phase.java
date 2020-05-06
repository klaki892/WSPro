package ton.klay.wspro.core.game.formats.standard.phases.turnPhases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.phase.GamePhase;
import ton.klay.wspro.core.api.game.phase.TurnPhase;
import ton.klay.wspro.core.game.Duel;

//todo phase interface docs
public abstract class Phase implements GamePhase {
    private static final Logger log = LogManager.getLogger();


    protected TurnPhase currentPhase;
    protected Duel duel;

    protected Phase(Duel duel, TurnPhase currentPhase) {
        this.currentPhase = currentPhase;
        this.duel = duel;
    }


    @Override
    public TurnPhase getPhaseName() {
        return currentPhase;
    }

}

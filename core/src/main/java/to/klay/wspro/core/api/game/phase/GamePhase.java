package to.klay.wspro.core.api.game.phase;

import to.klay.wspro.core.api.game.GameEntity;
import to.klay.wspro.core.game.formats.standard.phases.TurnPhase;

public interface GamePhase extends GameEntity {

    /**
     * Starts executing the actions which are preformed within this phase
     */
    void startPhase();

    TurnPhase getIdentifier();




}

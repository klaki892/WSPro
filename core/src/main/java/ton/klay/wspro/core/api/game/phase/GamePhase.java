package ton.klay.wspro.core.api.game.phase;

import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.game.formats.standard.phases.TurnPhase;

public interface GamePhase {

    /**
     * Starts executing the actions which are preformed within this phase
     */
    void startPhase();

    TurnPhase getIdentifier();




}

package ton.klay.wspro.core.api.game.phase;

import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.setup.GameContext;

public interface GamePhase {

    /**
     * Starts executing the actions which are preformed within this phase
     * @param context  the context in which this phase is being played
     * @param owner  the owning player of the phase
     * @throws EndPhaseForcefully
     */
    void startPhase(GameContext context, GamePlayer owner) throws EndPhaseForcefully;


    String getPhaseName();
}

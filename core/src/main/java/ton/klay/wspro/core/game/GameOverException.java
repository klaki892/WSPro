package ton.klay.wspro.core.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.player.GamePlayer;

import java.util.List;

/**
 * Called when a game is declared over. Used to short-circuit a game in the middle of resolving any action.
 */
public class GameOverException extends RuntimeException {

    private static final Logger log = LogManager.getLogger();
    private final List<GamePlayer> losingPlayers;

    public GameOverException(List<GamePlayer> losingPlayers) {
        this.losingPlayers = losingPlayers;
    }

    public List<GamePlayer> getLosingPlayers() {
        return losingPlayers;
    }
}

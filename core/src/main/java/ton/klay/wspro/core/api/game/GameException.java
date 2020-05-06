package ton.klay.wspro.core.api.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The base exception for what can occur in a game
 */
public class GameException extends Exception {

    private static final Logger log = LogManager.getLogger();

    //todo logging of all errors
    public GameException() {
        super();
    }

    public GameException(String message) {
        super(message);
    }

    public GameException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameException(Throwable cause) {
        super(cause);
    }
}

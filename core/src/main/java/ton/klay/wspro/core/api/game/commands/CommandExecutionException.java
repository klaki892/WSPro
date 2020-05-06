package ton.klay.wspro.core.api.game.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.GameException;
import ton.klay.wspro.core.api.game.GameRuntimeException;

/**
 * Execption used when a game
 */
public class CommandExecutionException extends GameException {

    private static final Logger log = LogManager.getLogger();

    public CommandExecutionException() {
        super();
    }

    public CommandExecutionException(String message) {
        super(message);
    }

    public CommandExecutionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandExecutionException(Throwable cause) {
        super(cause);
    }

}

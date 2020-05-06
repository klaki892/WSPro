package ton.klay.wspro.core.game.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.commands.CommandExecutionException;

/**
 * This exception is specifically for when a player does something wrong (e.g. wrong argument count)
 * <b>CALL THIS EXCEPTION BEFORE ANY EXECUTION OF THE COMMAND.</b>
 * This exception is the only exception that should not end the game because of a failed command execution.
 */
public class InvalidCommandFromPlayerException extends CommandExecutionException {

    private static final Logger log = LogManager.getLogger();

    public InvalidCommandFromPlayerException() {
        super();
    }

    public InvalidCommandFromPlayerException(String message) {
        super(message);
    }

    public InvalidCommandFromPlayerException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCommandFromPlayerException(Throwable cause) {
        super(cause);
    }

}

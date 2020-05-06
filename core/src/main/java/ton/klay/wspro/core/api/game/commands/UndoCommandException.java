package ton.klay.wspro.core.api.game.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UndoCommandException extends CommandExecutionException {

    public UndoCommandException() {
        super();
    }

    public UndoCommandException(String message) {
        super(message);
    }

    public UndoCommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public UndoCommandException(Throwable cause) {
        super(cause);
    }
}

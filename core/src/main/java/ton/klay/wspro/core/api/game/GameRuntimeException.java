package ton.klay.wspro.core.api.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameRuntimeException extends RuntimeException {

    private static final Logger log = LogManager.getLogger();
    //todo log errors in the logger

    public GameRuntimeException() {
        super();
    }

    public GameRuntimeException(String message) {
        super(message);
    }

    public GameRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameRuntimeException(Throwable cause) {
        super(cause);
    }

}

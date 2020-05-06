package ton.klay.wspro.core.game.scripting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.GameRuntimeException;

public class IllegalScriptExecutionException extends GameRuntimeException {

    private static final Logger log = LogManager.getLogger();

    public IllegalScriptExecutionException(String message){
        super(message);
        log.fatal(message);
    }

    public IllegalScriptExecutionException(String message, Throwable cause){
        super(message, cause);
        log.fatal(message);
        log.trace(cause);
    }
}

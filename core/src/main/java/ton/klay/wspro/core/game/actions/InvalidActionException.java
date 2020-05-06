package ton.klay.wspro.core.game.actions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.GameRuntimeException;

public class InvalidActionException extends GameRuntimeException {

    private static final Logger log = LogManager.getLogger();

    public long timeStamp = System.currentTimeMillis();

    public InvalidActionException(String message){
        super(message);
        log.error(message);
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}

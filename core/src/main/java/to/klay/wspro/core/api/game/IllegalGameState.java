package to.klay.wspro.core.api.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Exception that occurs from the game being in an Illegal {@link GameStatus } to preform an action.
 */
public class IllegalGameState extends GameRuntimeException {

    private static final Logger log = LogManager.getLogger();


}

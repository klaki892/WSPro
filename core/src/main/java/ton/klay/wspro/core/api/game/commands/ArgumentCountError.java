package ton.klay.wspro.core.api.game.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.GameException;

public class ArgumentCountError extends CommandExecutionException {

    private static final Logger log = LogManager.getLogger();

    public ArgumentCountError(int expected, int actual){
        //todo extract string?
        super("Expected: " + expected + ". Actual: " + actual);
    }

}

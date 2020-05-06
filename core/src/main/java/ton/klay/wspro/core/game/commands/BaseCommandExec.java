package ton.klay.wspro.core.game.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.commands.*;

public abstract class BaseCommandExec implements CommandExecution {

    protected static void argCountCheck(int expectedArgCount, String[] args, CommandSender sender) throws CommandExecutionException {
        if (args.length != expectedArgCount) {
            ArgumentCountError argumentCountError = new ArgumentCountError(expectedArgCount, args.length);
            if (sender.getSenderType() != CommandSenderType.PLAYER) {
                throw argumentCountError;
            } else {
                throw new InvalidCommandFromPlayerException(argumentCountError);
            }
        }
    }

}

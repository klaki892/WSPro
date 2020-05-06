package ton.klay.wspro.core.game.commands.lowlevel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.commands.CommandExecution;
import ton.klay.wspro.core.api.game.commands.CommandExecutionException;
import ton.klay.wspro.core.api.game.commands.CommandSender;
import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.game.events.lowlevel.GameErrorEvent;

public class GameErrorCommand implements CommandExecution {


    public static final String CMD_NAME = "GAMEERROR";

    public GameErrorCommand(){}

    //GAMEERROR

    @Override
    public void execute(GameContext context, String[] args, CommandSender sender) throws CommandExecutionException {
        context.getEventManager().issueEvent(new GameErrorEvent());
    }

    @Override
    public void undo(GameContext context, String[] args) throws CommandExecutionException {

    }
}

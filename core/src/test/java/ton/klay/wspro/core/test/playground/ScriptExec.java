package ton.klay.wspro.core.test.playground;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.commands.CommandExecution;
import ton.klay.wspro.core.api.game.commands.CommandSender;
import ton.klay.wspro.core.api.game.setup.GameContext;

public class ScriptExec implements CommandExecution {

    private static final Logger log = LogManager.getLogger();

    @Override
    public void execute(GameContext context, String[] args, CommandSender sender) {
        System.out.println("I let Script engines do my thing");
    }

    @Override
    public void undo(GameContext context, String[] args) {
        System.out.println("A Script defines what i do here");
    }
}

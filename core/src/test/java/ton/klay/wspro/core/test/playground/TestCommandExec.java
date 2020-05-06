package ton.klay.wspro.core.test.playground;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.commands.CommandExecution;
import ton.klay.wspro.core.api.game.commands.CommandSender;
import ton.klay.wspro.core.api.game.setup.GameContext;

import java.util.Arrays;

public class TestCommandExec implements CommandExecution {

    private static final Logger log = LogManager.getLogger();

    public TestCommandExec(){}

    @Override
    public void execute(GameContext context, String[] args, CommandSender sender) {
        System.out.println("I do things!? Here are my arguments:" + Arrays.toString(args));
    }

    @Override
    public void undo(GameContext context, String[] args) {
        System.out.println("I undo things?!");
    }
}

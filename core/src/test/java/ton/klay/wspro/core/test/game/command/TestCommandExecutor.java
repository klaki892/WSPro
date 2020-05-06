package ton.klay.wspro.core.test.game.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.commands.CommandExecutionException;
import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.api.game.commands.Command;
import ton.klay.wspro.core.api.game.commands.CommandExecutor;

public class TestCommandExecutor implements CommandExecutor {

    private static final Logger log = LogManager.getLogger();

    GameContext gameContext;

    public TestCommandExecutor(){
        gameContext = new TestGameContext();
    }
    @Override
    public GameContext getGameContext() {
        return gameContext;
    }

    @Override
    public void setGameContext(GameContext gameContext) {
        this.gameContext = gameContext;
    }

    @Override
    public void issueCommand(Command command) {
        System.out.println(command.toString());
        try {
            command.getExecution().execute(gameContext, command.getArgs(), command.getSender());
        } catch (CommandExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isIdle() {
        return false;
    }
}

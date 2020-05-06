package ton.klay.wspro.core.api.game.commands;

import ton.klay.wspro.core.api.game.setup.GameContext;

import java.util.concurrent.ExecutionException;

public interface CommandExecution {

    void execute(GameContext context, String[] args, CommandSender sender) throws CommandExecutionException;

    void undo(GameContext context, String[] args) throws  CommandExecutionException;
}

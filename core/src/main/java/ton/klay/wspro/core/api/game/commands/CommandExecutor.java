package ton.klay.wspro.core.api.game.commands;

import ton.klay.wspro.core.api.game.setup.GameContext;

public interface CommandExecutor {
    GameContext getGameContext();

    void setGameContext(GameContext gameContext);

    void issueCommand(Command command);

    boolean isIdle();
}

package ton.klay.wspro.core.test.game.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.GameStatus;
import ton.klay.wspro.core.api.game.LoseConditions;
import ton.klay.wspro.core.api.game.commands.CommandDecoder;
import ton.klay.wspro.core.api.game.commands.CommandExecutor;
import ton.klay.wspro.core.api.game.commands.CommandSender;
import ton.klay.wspro.core.api.game.communication.GameCommunicationManager;
import ton.klay.wspro.core.api.game.events.EventManager;
import ton.klay.wspro.core.api.game.setup.GameConfig;
import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.communication.Communicator;
import ton.klay.wspro.core.api.game.setup.GameFormat;
import ton.klay.wspro.core.api.scripting.ScriptEngine;

import java.util.*;

public class TestGameContext implements GameContext {

    private static final Logger log = LogManager.getLogger();


    @Override
    public CommandDecoder getCommandDecoder() {
        return null;
    }

    @Override
    public CommandExecutor getCommandExecutor() {
        return null;
    }

    @Override
    public GameStatus getGameStatus() {
        return null;
    }

    @Override
    public EventManager getEventManager() {
        return null;
    }

    @Override
    public Collection<GamePlayer> getPlayers() {
        return null;
    }

    @Override
    public Collection<Communicator> getSpectators() {
        return null;
    }

    @Override
    public boolean addSpectator(Communicator communicator) {
        return false;
    }

    @Override
    public ScriptEngine getScriptEngine() {
        return null;
    }

    @Override
    public String getGameID() {
        return null;
    }

    @Override
    public GameFormat getGameFormat() {
        return null;
    }

    @Override
    public GameConfig getGameConfig() {
        return null;
    }

    @Override
    public GameCommunicationManager getGameCommunicationManager() {
        return null;
    }

    @Override
    public Random getRandom() {
        return null;
    }

    @Override
    public CommandSender getCommandSender() {
        return null;
    }

    @Override
    public void endGame(LoseConditions loseCondition, Collection<GamePlayer> losers) {

    }
}

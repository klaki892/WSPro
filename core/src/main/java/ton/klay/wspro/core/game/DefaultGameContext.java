package ton.klay.wspro.core.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.GameStatus;
import ton.klay.wspro.core.api.game.LoseConditions;
import ton.klay.wspro.core.api.game.commands.*;
import ton.klay.wspro.core.api.game.communication.Communicator;
import ton.klay.wspro.core.api.game.communication.GameCommunicationManager;
import ton.klay.wspro.core.api.game.events.EventManager;
import ton.klay.wspro.core.api.game.phase.GamePhase;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.setup.GameConfig;
import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.api.game.setup.GameFormat;
import ton.klay.wspro.core.api.scripting.ScriptEngine;
import ton.klay.wspro.core.game.commands.DefaultWeissCommandDecoder;
import ton.klay.wspro.core.game.commands.lowlevel.GameStartCommand;
import ton.klay.wspro.core.game.communication.DefaultGameCommunicationManager;
import ton.klay.wspro.core.game.events.gameissued.GameEndEvent;
import ton.klay.wspro.core.game.events.lowlevel.GameErrorEvent;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.*;

/**
 * The default implementation of a game context which runs a weiss game
 */
public class DefaultGameContext implements GameContext{

    private static final Logger log = LogManager.getLogger();

    private final CommandExecutor commandExecutor;
    private final EventManager eventManager;
    private final Collection<GamePlayer> playerList;
    private final Collection<Communicator> spectatorList;
    private final GameFormat gameFormat;
    private final GameConfig gameConfig;
    private final ScriptEngine scriptEngine;
    private final String gameID;
    private final GameCommunicationManager gameCommunicationManager;
    private final CommandDecoder commandDecoder;

    /**
     * Random used for RNG, in this implementation random in instantiated when its first called.
     * @see #getRandom()
     */
    private Random random;

    private GameStatus gameStatus;

    private CommandSender gameCommandSender;

    private int errorCounter;

    public DefaultGameContext(CommandExecutor commandExecutor, EventManager eventHandler, Collection<GamePlayer> playerList,
                              Collection<Communicator> spectatorList, GameFormat gameFormat, GameConfig gameConfig,
                              ScriptEngine scriptEngine, GameCommunicationManager gameCommunicationManager, CommandDecoder commandDecoder) {

        gameID = UUID.randomUUID().toString();
        log.debug("Game ID created: " + gameID);

        this.eventManager = eventHandler;

        this.commandExecutor = commandExecutor;
        commandExecutor.setGameContext(this);

        this.playerList = playerList;
        this.spectatorList = spectatorList;
        this.gameFormat = gameFormat;
        this.gameConfig = gameConfig;
        this.scriptEngine = scriptEngine;

        //create our command sender
        gameCommandSender = new GameCommandSender();

        //create the communication manager
        if (gameCommunicationManager == null) {
            this.gameCommunicationManager = new DefaultGameCommunicationManager(gameID, this);
        } else {
            gameCommunicationManager.setContext(this);
            this.gameCommunicationManager = gameCommunicationManager;
        }

        //create the commandDecoder
        if (commandDecoder == null) {
            this.commandDecoder= new DefaultWeissCommandDecoder(scriptEngine);
        } else {
            this.commandDecoder= commandDecoder;
        }


        //register players (and spectators) for relevant messages
        for (GamePlayer gamePlayer : playerList) {
            this.gameCommunicationManager.addCommunicator(gamePlayer.getCommunicator() , CommandSenderType.PLAYER);
        }
        for (Communicator spectator : spectatorList){
            this.gameCommunicationManager.addCommunicator(spectator, CommandSenderType.OBSERVER);
        }

        createGameErrorListener();

        startGame(gameFormat.getStartPhase());
    }

    /**
     * Starts the game
     * @param startPhase the phase in which we will issue our first command
     */
    private void startGame(GamePhase startPhase) {
        log.debug("Beginning game initialization. ID: " + gameID);


        //let the phases take over the game. the game now begins.
        log.info("Issuing Game Start Command");
        gameCommunicationManager.decode(() -> GameStartCommand.CMD_NAME, new GameCommandSender());

        setGameStatus(GameStatus.PLAYING);

    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    @Override
    public CommandDecoder getCommandDecoder() {
        return commandDecoder;
    }

    @Override
    public CommandExecutor getCommandExecutor() {
        return commandExecutor;
    }

    @Override
    public GameStatus getGameStatus() {
        return null;
    }

    @Override
    public EventManager getEventManager() {
        return eventManager;
    }

    @Override
    public Collection<GamePlayer> getPlayers() {
        return playerList;
    }

    @Override
    public Collection<Communicator> getSpectators() {
        return spectatorList;
    }

    @Override
    public boolean addSpectator(Communicator communicator) {
        //todo
        return false;
    }

    @Override
    public ScriptEngine getScriptEngine() {
        return scriptEngine;
    }

    @Override
    public String getGameID() {
        return gameID;
    }

    @Override
    public GameFormat getGameFormat() {
        return gameFormat;
    }

    @Override
    public GameConfig getGameConfig() {
        return gameConfig;
    }

    @Override
    public GameCommunicationManager getGameCommunicationManager() {
        return gameCommunicationManager;
    }

    @Override
    public Random getRandom() {
        if (random != null)
            return random;
        else {
            //use a SecureRandom to generate entropy, then use a deterministic method for the rest of the game for repeatability
            long seed = ByteBuffer.wrap(new SecureRandom().generateSeed(8)).getLong();
            log.info("Random Seed Generated: " + seed);
            return random = new Random(seed);
        }
    }

    @Override
    public CommandSender getCommandSender() {
        return gameCommandSender;
    }

    @Override
    public void endGame(LoseConditions loseCondition, Collection<GamePlayer> losers) {
        //notify, release player communication handles, and change status and close
        StringJoiner joiner = new StringJoiner(" ");
        for (GamePlayer loser : losers) {
            joiner.add(loser.getCommunicator().getID());
        }
        log.info("Game Ended. " +
                    "Losers: " + joiner.toString() + " " +
                    "Condition: " + loseCondition.name());


        setGameStatus(GameStatus.FINISHED);

        //todo output game statistics?

        //alert everything the game is shutting down
        eventManager.issueEvent(new GameEndEvent(gameID));
    }

    /**
     * Creates a {@link ton.klay.wspro.core.game.events.lowlevel.GameErrorEvent} listener.
     * When the game has received enough GameErrorEvents equal to the number of players, the game will shut down.
     */
    private void createGameErrorListener() {
        errorCounter = 0;
        getEventManager().addEventListener( listener -> {
            if (++errorCounter >= getPlayers().size()) {
                log.fatal("All players reported that a game error has occurred, stopping game.");
                endGame(LoseConditions.GAME_ERROR, Collections.emptyList());
            }
        }, GameErrorEvent.EVENT_NAME);
    }

}

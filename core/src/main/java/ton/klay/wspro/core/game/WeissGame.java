package ton.klay.wspro.core.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.GameRuntimeException;
import ton.klay.wspro.core.api.game.IGame;
import ton.klay.wspro.core.api.game.commands.CommandDecoder;
import ton.klay.wspro.core.api.game.commands.CommandExecutor;
import ton.klay.wspro.core.api.game.communication.Communicator;
import ton.klay.wspro.core.api.game.communication.GameCommunicationManager;
import ton.klay.wspro.core.api.game.events.EventManager;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.player.PlayerDeckPair;
import ton.klay.wspro.core.api.game.setup.GameConfig;
import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.api.game.setup.GameFormat;
import ton.klay.wspro.core.api.scripting.ScriptEngine;
import ton.klay.wspro.core.game.events.WeissEventManager;
import ton.klay.wspro.core.game.scripting.lua.LuaScriptEngine;

import java.util.ArrayList;
import java.util.Collection;

public class WeissGame implements IGame {

    private static final Logger log = LogManager.getLogger();
    private GameCommunicationManager commManager;
    private Collection<GamePlayer> playerList = new ArrayList<>();
    private Collection<Communicator> spectatorList = new ArrayList<>();

    private GameFormat gameFormat;
    private GameConfig gameConfig;

    /**
     * Prepares of game of weiss with a connection of players and a defined game configuration and format
     * @param players The players who will be participating in the game
     * @param spectators a list of people who will be observing the game but not playing
     * @param gameConfig the general configuration for the game
     * @param gameFormat the specific format that the game will be played in
     * @throws IllegalArgumentException if the number of players is different from what the game format allows.
     */
    public WeissGame(Collection<PlayerDeckPair> players, Collection<Communicator> spectators,
                     GameConfig gameConfig, GameFormat gameFormat) throws IllegalArgumentException {
        log.info("Preparing new Weiss Game");

        //setup all configurations
        setGameFormat(gameFormat);

        setGameConfig(gameConfig);

        //set the players and spectators
        for (PlayerDeckPair player : players) {
            addPlayer(player);
        }

        for (Communicator spectator : spectators) {
            addSpectator(spectator);
        }

        log.info("Weiss game initialization complete");
    }

    @Override
    public GameContext startGame() throws GameRuntimeException {
        log.debug("Attempting to start Weiss game");
        // check to make sure game format rules are met
        if (gameFormat.requirementsMet(playerList)){

            log.debug("creating script engine");
            //createScriptEngine
            ScriptEngine scriptEngine = new LuaScriptEngine(gameConfig);

            //create command handler
            log.debug("Creating command handler");
            CommandExecutor commandHandler = new WeissCommandExecutor();

            //create event handler
            log.debug("Creating event manager ");
            EventManager eventManager = new WeissEventManager();

            //create Comm Decoder
            log.debug("Creating command decoder");
            CommandDecoder commDecoder = new DefaultWeissCommandDecoder(scriptEngine);

            log.info("Launching game");
            //create the new game context
            return new DefaultGameContext(commandHandler, eventManager, playerList, spectatorList,
                    gameFormat, gameConfig, scriptEngine, commManager, commDecoder);
        }
        else
            throw new GameRuntimeException("game does not meet format requirements:" + gameFormat.getErrors());

    }

    @Override
    public void setGameFormat(GameFormat gameFormat)  {
        if (gameFormat != null){
            log.info("set new game format: " + gameFormat.getName());
            this.gameFormat = gameFormat;
        } else {
            throw new GameRuntimeException("Received null game format.");
        }
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
    public void setGameConfig(GameConfig configuration) {
        if (configuration != null) {
            log.info("set new game configuration");
            this.gameConfig = configuration;
        } else
            throw new GameRuntimeException("Received null configuration.");
    }

    @Override
    public void setGameCommunicationManager(GameCommunicationManager communicationManager) {
        if (communicationManager != null) {
            log.info("set new game communication manager.");
            this.commManager= communicationManager;
        } else
            throw new GameRuntimeException("Received null Communication manager.");
    }

    @Override
    public boolean addPlayer(PlayerDeckPair player) {
        if (player != null) {
            playerList.add(new WeissGamePlayer(player));
            log.info("Added new Player to game");
            return true;
        } else
            throw new GameRuntimeException("Received null player.");

    }

    @Override
    public boolean addSpectator(Communicator spectator) {
        if (spectator != null) {
            spectatorList.add(spectator);
            return true;
        }  else
            throw new GameRuntimeException("Received empty spectator.");

    }
}

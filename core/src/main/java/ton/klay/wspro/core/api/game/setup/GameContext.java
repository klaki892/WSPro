package ton.klay.wspro.core.api.game.setup;

import ton.klay.wspro.core.api.game.GameStatus;
import ton.klay.wspro.core.api.game.LoseConditions;
import ton.klay.wspro.core.api.game.commands.CommandDecoder;
import ton.klay.wspro.core.api.game.commands.CommandExecutor;
import ton.klay.wspro.core.api.game.commands.CommandSender;
import ton.klay.wspro.core.api.game.communication.Communicator;
import ton.klay.wspro.core.api.game.communication.GameCommunicationManager;
import ton.klay.wspro.core.api.game.events.EventManager;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.scripting.ScriptEngine;

import java.util.Collection;
import java.util.Random;

/**
 * Representation of an implementation for a running game.<br/>
 * The GameContext contains methods for setting and getting all resources that pertain to a particular game.
 * As the central hub of a running/ended game all information flow should come through the object that implements
 * this interface. (e.g. if a card needs information about the play field, it needs to reference a game context to obtain that information)<br/>
 * Because of this centralized structure, access control and configuration for information, command execution, and event notification
 * can be easily managed.
 */
public interface GameContext {


    CommandDecoder getCommandDecoder();

    CommandExecutor getCommandExecutor();

    GameStatus getGameStatus();

    /**
     * @return an eventHandler meant to handle all inter-game events.
     */
    EventManager getEventManager();


    Collection<GamePlayer> getPlayers();

    Collection<Communicator> getSpectators();

    boolean addSpectator(Communicator communicator);

    ScriptEngine getScriptEngine();

    String getGameID();

    GameFormat getGameFormat();

    GameConfig getGameConfig();

    GameCommunicationManager getGameCommunicationManager();

    /**
     * Returns a Random instance that the Game can use for random events (the only real example is shuffling)
     * Although implementation on returning a Random is independent, using a Deterministic instance of Random (e.g. NOT {@link java.security.SecureRandom} )
     * Will allow for accurate replay simulation of games.
     * @return
     */
    Random getRandom();

    CommandSender getCommandSender();

    /**
     * Ends the game, marking a complete game and handling cleanup <br/>
     * Recommended cleanup steps would be notifying everyone who is watching the game condition (server, players etc)<br/>
     * Closing out a logger with the stats of the game.<br/>
     * A list of losers is all the necessary info a game needs to complete as per the comprehensive rules.
     * if you wish to determine winners, winners are simply players who didnt lose.
     * @see <code>Weiss Schwarz Rule 1.2.1</code>
     * @param loseCondition the lose condition indicating the reason for the list of losers.
     * @param losers a list of players who met the losing condition
     */
    void endGame(LoseConditions loseCondition, Collection<GamePlayer> losers);

}

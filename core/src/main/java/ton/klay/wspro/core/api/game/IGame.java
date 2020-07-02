package ton.klay.wspro.core.api.game;

import ton.klay.wspro.core.api.game.communication.Communicator;
import ton.klay.wspro.core.api.game.communication.GameCommunicationManager;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.player.PlayerDeckPair;
import ton.klay.wspro.core.api.game.setup.GameConfig;
import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.api.game.setup.GameFormat;

import java.util.Collection;

/**
 * Represents a Game and all of the interactions that can be done with a game from the outside
 */
public interface IGame {


    /**
     * Begins a game, changing the {@link GameStatus} and locking most settable parameters.
     *
     * @return A {@link GameContext} which allows for interacting with this instance of the game
     * @throws GameRuntimeException if an error occurs over the duration of the game
     */
    GameContext startGame() throws GameRuntimeException;

    /**
     * Sets the ruleset that this game will adhere to throughout its game.<br/>
     *
     * @param gameFormat A gameFormat containing all restrictions and rules to set up the game
     */
    void setGameFormat(GameFormat gameFormat);

    Collection<GamePlayer> getPlayers();

    Collection<Communicator> getSpectators();

    void setGameConfig(GameConfig configuration);

    void setGameCommunicationManager(GameCommunicationManager communicationManager);



    boolean addPlayer(PlayerDeckPair player);

    boolean addSpectator(Communicator spectator);
}

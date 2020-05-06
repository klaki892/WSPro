package ton.klay.wspro.core.api.game.player;

import ton.klay.wspro.core.api.game.IDeck;
import ton.klay.wspro.core.api.game.communication.Communicator;

/**
 * Interface that just ensures the passing along of a Player's ability to communicate and a Deck
 */
public interface PlayerDeckPair {
    Communicator getCommunicator();

    IDeck getDeck();

}

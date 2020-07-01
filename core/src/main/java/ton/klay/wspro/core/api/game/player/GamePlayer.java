package ton.klay.wspro.core.api.game.player;

import ton.klay.wspro.core.api.game.IDeck;
import ton.klay.wspro.core.api.game.communication.Communicator;
import ton.klay.wspro.core.api.game.field.PlayArea;

public interface GamePlayer {
    PlayArea getPlayArea();

    @Deprecated
    Communicator getCommunicator();

    PlayerController getController();

    IDeck getDeck();

    void setPlayArea(PlayArea playArea);

}

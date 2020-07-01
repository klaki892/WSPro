package ton.klay.wspro.core.api.game.player;

import ton.klay.wspro.core.api.game.IDeck;
import ton.klay.wspro.core.api.game.Ownable;
import ton.klay.wspro.core.api.game.communication.Communicator;
import ton.klay.wspro.core.api.game.field.PlayArea;

public interface GamePlayer extends Ownable {
    PlayArea getPlayArea();

    Communicator getCommunicator();

    IDeck getDeck();

    void setPlayArea(PlayArea playArea);

    void setCommunicator(Communicator communicator);

}

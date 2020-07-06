package ton.klay.wspro.core.api.game.player;

import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.api.game.IDeck;
import ton.klay.wspro.core.api.game.field.PlayArea;
import ton.klay.wspro.core.game.Game;

public interface GamePlayer extends GameEntity {
    PlayArea getPlayArea();

    PlayerController getController();

    IDeck getDeck();

    void setPlayArea(PlayArea playArea);

    Game getGame();
}

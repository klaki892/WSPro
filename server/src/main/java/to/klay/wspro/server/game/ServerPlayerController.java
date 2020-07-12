package to.klay.wspro.server.game;

import ton.klay.wspro.core.api.game.player.PlayerController;

public interface ServerPlayerController extends PlayerController {
    void setPlayerReadied(boolean playerReadied);

    boolean isPlayerReady();
}

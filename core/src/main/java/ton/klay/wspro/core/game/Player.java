package ton.klay.wspro.core.game;

import ton.klay.wspro.core.api.game.PaperDeck;
import ton.klay.wspro.core.api.game.player.PlayerController;
import ton.klay.wspro.core.api.game.setup.GameLocale;

public interface Player {

    PaperDeck getDeck();

    String getPlayerName();

    GameLocale getPlayerLocale();

    PlayerController getController();

}

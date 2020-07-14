package to.klay.wspro.core.game;

import to.klay.wspro.core.api.game.PaperDeck;
import to.klay.wspro.core.api.game.player.PlayerController;
import to.klay.wspro.core.api.game.setup.GameLocale;

public interface Player {

    PaperDeck getDeck();

    String getName();

    GameLocale getPlayerLocale();

    PlayerController getController();

}

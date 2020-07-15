package to.klay.wspro.core.game.formats.standard.phases;

import to.klay.wspro.core.api.game.PaperDeck;
import to.klay.wspro.core.api.game.player.PlayerController;
import to.klay.wspro.core.api.game.setup.GameLocale;
import to.klay.wspro.core.game.Game;
import to.klay.wspro.core.game.Player;

public class TestPlayer implements Player {

    private final String playerName;
    private final PaperDeck deck;

    Game game;
    PlayerController controller;

    public TestPlayer(String playerName, PaperDeck deck, PlayerController controller) {
        this.playerName = playerName;
        this.deck = deck;
        this.controller = controller;
    }

    @Override
    public PlayerController getController() {
        return controller;
    }

    @Override
    public PaperDeck getDeck() {
        return deck;
    }

    @Override
    public String getName() {
        return playerName;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public GameLocale getPlayerLocale() {
        return GameLocale.EN;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "@" + Integer.toHexString(hashCode());
    }
}
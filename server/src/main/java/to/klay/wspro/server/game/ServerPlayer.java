package to.klay.wspro.server.game;

import com.google.common.base.MoreObjects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.PaperDeck;
import ton.klay.wspro.core.api.game.player.PlayerController;
import ton.klay.wspro.core.api.game.setup.GameLocale;
import ton.klay.wspro.core.game.Player;

public class ServerPlayer implements Player {

    private static final Logger log = LogManager.getLogger();
    private final String name;
    private PaperDeck deck;
    private GameLocale playerLocale;
    private PlayerController controller;

    public ServerPlayer(String name ){
        this.name = name;
    }



    public void setPlayerLocale(GameLocale playerLocale) {
        this.playerLocale = playerLocale;
    }

    public void setController(PlayerController controller) {
        this.controller = controller;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public PaperDeck getDeck() {
        return deck;
    }

    public void setDeck(PaperDeck deck){
        this.deck = deck;
    }


    @Override
    public GameLocale getPlayerLocale() {
        return playerLocale;
    }

    @Override
    public PlayerController getController() {
        return controller;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .toString();
    }
}

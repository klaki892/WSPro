package ton.klay.wspro.core.api.game.player;

import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.api.game.PaperDeck;
import ton.klay.wspro.core.api.game.field.PlayArea;
import ton.klay.wspro.core.api.game.setup.GameLocale;
import ton.klay.wspro.core.game.Game;
import ton.klay.wspro.core.game.Player;

public class GamePlayer implements GameEntity {


    private final Game game;
    private final Player player;
    private PlayArea playArea;

    public GamePlayer(Game game, Player player) {
        this.game = game;
        this.player = player;
    }

    public PlayArea getPlayArea(){
        return playArea;
    }

    public PlayerController getController(){
        return player.getController();
    }

    public String getPlayerName(){
        return player.getName();
    }

    public PaperDeck getPaperDeck(){
        return player.getDeck();
    }

    public void setPlayArea(PlayArea playArea){
        this.playArea = playArea;
    }

    public Game getGame(){
        return game;
    }

    @Override
    public GamePlayer getMaster() {
        return this;
    }

    public GameLocale getPlayerLocale(){
        return player.getPlayerLocale();
    }
}

package to.klay.wspro.core.api.game.player;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import to.klay.wspro.core.api.game.GameEntity;
import to.klay.wspro.core.api.game.PaperDeck;
import to.klay.wspro.core.api.game.field.PlayArea;
import to.klay.wspro.core.api.game.setup.GameLocale;
import to.klay.wspro.core.game.Game;
import to.klay.wspro.core.game.Player;
import to.klay.wspro.core.game.proto.ProtoGamePlayer;

@ProtoClass(ProtoGamePlayer.class)
public class GamePlayer implements GameEntity {


    private final transient Game game;
    private final transient Player player;
    private transient PlayArea playArea;

    @ProtoField
    private final String playerName; //used for serialization only

    public GamePlayer(Game game, Player player) {
        this.game = game;
        this.player = player;
        playerName = player.getName();
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

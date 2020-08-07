/*******************************************************************************
 *  WSPro - Trading Card Game Simulator
 *  Copyright (C) 2020  Klayton Killough
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published
 *  by the Free Software Foundation, version 3 of the License.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

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

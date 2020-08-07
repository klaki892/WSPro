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

package to.klay.wspro.server.game;

import com.google.common.base.MoreObjects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.game.PaperDeck;
import to.klay.wspro.core.api.game.setup.GameLocale;
import to.klay.wspro.core.game.Player;

public class ServerPlayer implements Player {

    private static final Logger log = LogManager.getLogger();
    private final String name;
    private PaperDeck deck;
    private GameLocale playerLocale;
    private ServerPlayerController controller;

    public ServerPlayer(String name ){
        this.name = name;
    }



    public void setPlayerLocale(GameLocale playerLocale) {
        this.playerLocale = playerLocale;
    }

    public void setController(ServerPlayerController controller) {
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
    public ServerPlayerController getController() {
        return controller;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .toString();
    }
}

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

package to.klay.wspro.core.test.manual;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.game.player.TestPlayerControllers;
import to.klay.wspro.core.game.Game;
import to.klay.wspro.core.game.Player;
import to.klay.wspro.core.game.formats.standard.phases.TestPlayer;
import to.klay.wspro.core.game.scripting.lua.TestLocalStorageLuaAbilityFinder;
import to.klay.wspro.core.test.TestLoggerConfigs;
import to.klay.wspro.core.test.endtoend.MockStandardDeck;
import to.klay.wspro.core.test.endtoend.TriggerLogger;

//test if we can successfully create a game
public class TestCommandLineGame {

    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args){

        TestLoggerConfigs.setLogLevel(Level.TRACE);


        Player player1 = new TestPlayer("TestPlayer1", new MockStandardDeck(), TestPlayerControllers::commandLinePlayChoiceMaker);
        Player player2 = new TestPlayer("TestPlayer2", new MockStandardDeck(), TestPlayerControllers::commandLinePlayChoiceMaker);

        Game game = new Game(player1, player2, new TestLocalStorageLuaAbilityFinder());

        game.getTriggerManager().register(new TriggerLogger());

        game.startGame();
    }


}

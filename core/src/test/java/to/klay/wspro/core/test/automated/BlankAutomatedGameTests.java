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

package to.klay.wspro.core.test.automated;

import com.google.common.eventbus.Subscribe;
import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import to.klay.wspro.core.api.game.GameStatus;
import to.klay.wspro.core.api.game.player.PlayerController;
import to.klay.wspro.core.api.game.player.TestPlayerControllers;
import to.klay.wspro.core.game.Game;
import to.klay.wspro.core.game.Player;
import to.klay.wspro.core.game.formats.standard.phases.TestPlayer;
import to.klay.wspro.core.game.proto.ProtoSerializable;
import to.klay.wspro.core.game.scripting.lua.TestLocalStorageLuaAbilityFinder;
import to.klay.wspro.core.test.TestLoggerConfigs;
import to.klay.wspro.core.test.endtoend.MockStandardDeck;
import to.klay.wspro.core.test.endtoend.TriggerLogger;

public class BlankAutomatedGameTests {


    @BeforeEach
    void setUp() {
        TestLoggerConfigs.setLogLevel(Level.DEBUG);
    };


    private void playAndLogLosers(Game game) {
        game.getTriggerManager().register(new TriggerLogger());
        game.startGame();
        Assertions.assertSame(GameStatus.FINISHED_SUCCESSFULLY, game.getGameState() );

        System.out.print("Losing Players: ");
        game.getLosingPlayers().forEach(player -> System.out.print(player.getPlayerName() + " "));
        System.out.println();
    }

    @Test
    void defaultChoicesBlankGameTest() {
        Player player1 = new TestPlayer("TestPlayer1", new MockStandardDeck(), TestPlayerControllers::defaultPlayChoiceMaker);
        Player player2 = new TestPlayer("TestPlayer2", new MockStandardDeck(), TestPlayerControllers::defaultPlayChoiceMaker);
        Game game = new Game(player1, player2, new TestLocalStorageLuaAbilityFinder());
        playAndLogLosers(game);
    }

    @Test
    void doNothingBlankGameTest(){
        Player player1 = new TestPlayer("TestPlayer1", new MockStandardDeck(), TestPlayerControllers::defaultPlayChoiceMaker);
        Player player2 = new TestPlayer("TestPlayer2", new MockStandardDeck(), TestPlayerControllers::defaultPlayChoiceMaker);
        Game game = new Game(player1, player2, new TestLocalStorageLuaAbilityFinder());
        playAndLogLosers(game);

    }

    @Test
    void serializationBlankGameTest(){

        PlayerController serializingDefaultPlayChoiceMaker = chooser -> {
            try {
                chooser.serializeToProto();
            } catch (RuntimeException ex){
                Assertions.fail(ex);
            }
            return TestPlayerControllers.defaultPlayChoiceMaker(chooser);
        };

        Player player1 = new TestPlayer("TestPlayer1", new MockStandardDeck(), serializingDefaultPlayChoiceMaker);
        Player player2 = new TestPlayer("TestPlayer2", new MockStandardDeck(), serializingDefaultPlayChoiceMaker);
        Game game = new Game(player1, player2, new TestLocalStorageLuaAbilityFinder());
        game.getTriggerManager().register(new Object(){
            @Subscribe
            void serializedOutput(ProtoSerializable protoSerializable){
                try {
                    protoSerializable.serializeToProto();
                } catch (RuntimeException ex){
                    Assertions.fail(ex);
                }
            }
        });
        playAndLogLosers(game);

    }

}
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

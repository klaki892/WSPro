package ton.klay.wspro.core.test.automated;

import org.apache.logging.log4j.Level;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ton.klay.wspro.core.api.game.GameStatus;
import ton.klay.wspro.core.api.game.player.TestPlayerControllers;
import ton.klay.wspro.core.game.Game;
import ton.klay.wspro.core.game.Player;
import ton.klay.wspro.core.game.formats.standard.phases.TestPlayer;
import ton.klay.wspro.core.game.scripting.lua.TestLocalStorageLuaAbilityFinder;
import ton.klay.wspro.core.test.TestLoggerConfigs;
import ton.klay.wspro.core.test.endtoend.MockStandardDeck;
import ton.klay.wspro.core.test.endtoend.TriggerLogger;

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

}
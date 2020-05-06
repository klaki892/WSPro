package ton.klay.wspro.core.test.endtoend;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import ton.klay.wspro.core.api.game.DeckConstructionFormats;
import ton.klay.wspro.core.api.game.IDeck;
import ton.klay.wspro.core.api.game.IGame;
import ton.klay.wspro.core.api.game.communication.Communicator;
import ton.klay.wspro.core.api.game.events.Event;
import ton.klay.wspro.core.api.game.events.EventListener;
import ton.klay.wspro.core.api.game.player.PlayerDeckPair;
import ton.klay.wspro.core.api.game.setup.GameConfig;
import ton.klay.wspro.core.api.game.setup.GameFormat;
import ton.klay.wspro.core.api.game.setup.GameLocale;
import ton.klay.wspro.core.game.WeissGame;
import ton.klay.wspro.core.game.events.gameissued.GameEndEvent;
import ton.klay.wspro.core.game.formats.standard.StandardWeissFormat;
import ton.klay.wspro.core.test.game.TestGameConfig;
import ton.klay.wspro.core.test.game.communication.TestCommandLineCommunicator;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

//test if we can successfully create a game
public class TestCreateGame {

    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args){

        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
//        loggerConfig.setLevel(Level.TRACE);
//        loggerConfig.setLevel(Level.FATAL);
        loggerConfig.setLevel(Level.OFF);
        ctx.updateLoggers();  // This causes all Loggers to refetch information from their LoggerConfig.

        //connect two players
        PlayerDeckPair player1 =  new PlayerDeckPair() {
            @Override
            public Communicator getCommunicator() {
                return new TestCommandLineCommunicator("player1");
            }

            @Override
            public IDeck getDeck() {
                return new TestDeck();
            }
        };

        PlayerDeckPair player2 = new PlayerDeckPair() {
            @Override
            public Communicator getCommunicator() {
                return new TestCommandLineCommunicator("player2");
            }

            @Override
            public IDeck getDeck() {
                return new TestDeck();
            }
        };


        Collection<PlayerDeckPair> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        //configuration of all settings
        GameFormat gameFormat = new StandardWeissFormat(GameLocale.EN, DeckConstructionFormats.FORMAT_NEO_STANDARD);
        GameConfig gameConfig = new TestGameConfig();

        //create new game
        IGame game = new WeissGame(players, Collections.emptyList(), gameConfig, gameFormat);

        //start the game
        CompletableFuture<Event> gameEndEvent = new CompletableFuture<>();

        EventListener gameEndListener = new EventListener() {
            @Override
            public void update(Event event) {
                if (event instanceof GameEndEvent)
                    gameEndEvent.complete(event);
            }
        };

        game.startGame();//.getEventManager().addEventListener(gameEndListener);

//        //wait until the game ends so the main thread kill it all prematurely
//        try {
//            gameEndEvent.get();
//            System.out.println("Shut down game");
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }


    }
}

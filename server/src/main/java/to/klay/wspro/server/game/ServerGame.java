package to.klay.wspro.server.game;

import com.google.common.eventbus.Subscribe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.server.ServerGameManager;
import ton.klay.wspro.core.api.game.GameStatus;
import ton.klay.wspro.core.game.Game;
import ton.klay.wspro.core.game.formats.standard.triggers.GameOverTrigger;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ServerGame extends Thread {

    private static final Logger log = LogManager.getLogger();
    private final ServerGameManager gameManager;
    Game weissGame;
    ServerPlayer player1, player2;
    Map<String, ServerPlayer> players;
    String gameID;

    public ServerGame(ServerGameManager gameManager, ServerPlayer player1, ServerPlayer player2){
        this.players = new ConcurrentHashMap<>();
        this.gameManager = gameManager;
        this.player1 = player1;
        this.player2 = player2;
        players.put(this.player1.getName(), this.player1);
        players.put(this.player2.getName(), this.player2);


        weissGame = new Game(this.player1, this.player2, gameManager.getAbilityFinder());
        //todo attach game logger?
        gameID = weissGame.getGameID();
        log.info("Created Weiss Game (" + gameID +") With " + this.player1 + " and " + this.player2 );
    }


    public boolean isStartable(){
        if (player1 == null || player2 == null) return false;


        if (weissGame.getGameState() != GameStatus.READY) return false;


        if (player1.getController().isPlayerReady() && player2.getController().isPlayerReady()) {
            log.info("Weiss Game " + gameID + " is fully ready to begin");
            return true;
        } else{
            return false;
        }
    }

    public String getGameID() {
        return gameID;
    }


    private void startIfBothReady() {
        if (isStartable()){
            forceStartGame();
        }
    }

    public void forceStartGame(){
        log.info("Starting Weiss Game " + gameID);
        start();
    }

    public List<ServerPlayer> getPlayers(){
        return Arrays.asList(player1, player2);
    }

    public Optional<ServerPlayer> getPlayer(String playerName){
        return Optional.ofNullable(players.get(playerName));
    }

    public Game getWeissGame() {
        return weissGame;
    }

    @Override
    public void run() {
        weissGame.getTriggerManager().register(this);
        weissGame.startGame();
    }

    @Subscribe
    private void cleanupGame(GameOverTrigger gameOverTrigger){
        //tell the server that this game is no longer active
        gameManager.removeFinishedGame(gameID);
    }
}

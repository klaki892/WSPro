package to.klay.wspro.server.game;

import com.google.common.eventbus.Subscribe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.server.grpc.gameplay.GrpcGameMessage;
import to.klay.wspro.server.grpc.gameplay.GrpcGameTrigger;
import to.klay.wspro.server.grpc.gameplay.GrpcPlayRequest;
import to.klay.wspro.server.grpc.gameplay.GrpcPlayResponse;
import ton.klay.wspro.core.api.game.player.PlayerController;
import ton.klay.wspro.core.game.Game;
import ton.klay.wspro.core.game.actions.PlayChoice;
import ton.klay.wspro.core.game.actions.PlayChooser;
import ton.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

public class GrpcPlayerController implements PlayerController {

    private static final Logger log = LogManager.getLogger();
    private final Game game;

    private boolean playerReadied = false;

    public GrpcPlayerController(Game game){
        this.game = game;
        game.getTriggerManager().register(this);
    }

    LinkedBlockingQueue<GrpcGameMessage> gameEventQueue = new LinkedBlockingQueue();

    CompletableFuture<List<Integer>> playRequest;

    public LinkedBlockingQueue<GrpcGameMessage> getGameEventQueue() {
        return gameEventQueue;
    }

    @Subscribe
    public void handleGameEvent(BaseTrigger event){

        GrpcGameTrigger trigger = GrpcGameTrigger.newBuilder()
                .setTriggerText(event.toString())
                .build();

        gameEventQueue.add(GrpcGameMessage.newBuilder().setTrigger(trigger).build());
    }

    public void answerPlayRequest(GrpcPlayResponse response){
        playRequest.complete(response.getChoiceNumberList());
    }

    @Override
    public List<PlayChoice> makePlayChoice(PlayChooser chooser) {

        AnswerablePlayChoice apw = new AnswerablePlayChoice(chooser);
        GrpcPlayRequest playRequest = GrpcPlayRequest.newBuilder().setChoiceBlock(apw.toString()).build();

        gameEventQueue.add(GrpcGameMessage.newBuilder().setRequest(playRequest).build());
        this.playRequest = new CompletableFuture<>();

        try {
            List<Integer> integers = this.playRequest.get(2, TimeUnit.MINUTES);
            return integers.stream().map(i -> chooser.getChoices().get(i)).collect(Collectors.toList());

        } catch (InterruptedException | ExecutionException e) {
            //
            log.error(e);
            return makeStupidChoice(chooser);
        } catch (TimeoutException timeout){
            log.error("Player choice timed out. Defaulting answer");

            return makeStupidChoice(chooser);
        }
    }

    public void setPlayerReadied(boolean playerReadied) {
        this.playerReadied = playerReadied;
    }

    @Override
    public boolean isPlayerReady() {
        return playerReadied;
    }

    private static List<PlayChoice> makeStupidChoice(PlayChooser chooser) {
        //todo replace with default choice AI

        List<PlayChoice> returnChoices = new ArrayList<>();
        for (int i = 0; i < chooser.getSelectionCount(); i++) {
            returnChoices.add(chooser.getChoices().get(i));
        }
        return returnChoices;
    }

    public Game getGame() {
        return game;
    }

    static class AnswerablePlayChoice {

        private HashMap<Integer, PlayChoice> map;
        private StringBuilder plainTextString = new StringBuilder();
        public AnswerablePlayChoice(PlayChooser chooser){
            map = new HashMap<>();
            for (int i = 0; i < chooser.getChoices().size(); i++) {
                PlayChoice choice = chooser.getChoices().get(i);
                map.put(i, choice);
                plainTextString.append(String.format("#%d: %s\n", i, choice));
            }

        }

        public List<PlayChoice> getChoices(List<Integer> values){
            return values.stream().map(integer -> map.get(integer)).collect(Collectors.toList());
        }

        @Override
        public String toString() {
            return plainTextString.toString();
        }


    }
}

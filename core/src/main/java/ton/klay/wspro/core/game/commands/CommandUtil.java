package ton.klay.wspro.core.game.commands;

import com.sun.xml.internal.ws.util.CompletedFuture;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.GameException;
import ton.klay.wspro.core.api.game.GameRuntimeException;
import ton.klay.wspro.core.api.game.commands.CommandExecutionException;
import ton.klay.wspro.core.api.game.commands.CommandExecutor;
import ton.klay.wspro.core.api.game.commands.CommandSender;
import ton.klay.wspro.core.api.game.communication.Communicable;
import ton.klay.wspro.core.api.game.events.Event;
import ton.klay.wspro.core.api.game.events.EventListener;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.game.commands.highlevel.PlayerResponseCommand;
import ton.klay.wspro.core.game.commands.highlevel.PlaytimingCommand;
import ton.klay.wspro.core.game.commands.lowlevel.GameEndCommand;
import ton.klay.wspro.core.game.commands.lowlevel.GameErrorCommand;
import ton.klay.wspro.core.game.commands.lowlevel.NextPhaseCommand;
import ton.klay.wspro.core.game.events.gameissued.GameEndEvent;
import ton.klay.wspro.core.game.formats.standard.phases.TurnPhase;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class CommandUtil {

    private static final Logger log = LogManager.getLogger();

    /**
     * Obtains a player reference from their ID.
     * @param context context in which the game is being played
     * @param playerID the ID of the player needing to be found
     * @return a gameplayer reference. Null if the reference doesnt exist.
     */
    public static GamePlayer getPlayerFromID(GameContext context, String playerID) {
        //dont use a hashmap, this is up and down too fast.
        for (GamePlayer gamePlayer : context.getPlayers()) {
            if (gamePlayer.getCommunicator().getID().equalsIgnoreCase(playerID))
                return gamePlayer;
        }
        return null;
    }

    /**
     * Wraps a series of String arguments into a Communicable object
     * @param args a list of arguments that will be converted to a communicable format. (space delimited string)
     * @return a communicable with the arguments passed into it
     */
    public static Communicable wrapCommunicable(String... args) {
        return () -> String.join(" ", args);
    }

    public static String[] issuePlayTimingAndWait(GameContext context, Communicable command , CommandSender sender) {
        //pull the responseID out of the command
        String[] commandArgs = command.toCommunicableString().split(" ");

        //check to make sure its a playtiming, else someone just called this function wrong
        if (commandArgs[0].equalsIgnoreCase(PlaytimingCommand.CMD_NAME)) {

            //grab the third argument, which should be the requestID
            String requestID = commandArgs[2];

            //setup the event listener for the response
            CompletableFuture<Event> eventResponse = new CompletableFuture<>();
            EventListener eventListener = new EventListener() {
                @Override
                public void update(Event event) {
                    if (event.getArgs()[0].equalsIgnoreCase(requestID)) {
                        context.getEventManager().removeEventListener(this);
                        eventResponse.complete(event);
                    } else if (event instanceof GameEndEvent){
                        eventResponse.completeExceptionally(new GameRuntimeException("Game Ended during playtiming."));
                    }
                }
            };

            log.trace("Creating PlayTiming Listener for requestID: " + requestID);
            context.getEventManager().addEventListener(eventListener, PlayerResponseCommand.CMD_NAME, GameEndCommand.CMD_NAME);

            //send the request out
            context.getGameCommunicationManager().decode(command, sender);

            //wait for the response, when obtained strip the responseID and return
            try {
                String[] responseArgs = eventResponse.get().getArgs();
                return Arrays.copyOfRange(responseArgs, 1, responseArgs.length);
            } catch (InterruptedException | ExecutionException e) {
                throw new GameRuntimeException("Error occurred retrieving response from PlayTiming.", e);
            }
        } else {
            throw new GameRuntimeException("Attempted to play a playtime listener for a nonPlaytime command:" + command.toCommunicableString());
        }
    }

    public static String[] issueInfoAndWait(GameContext context, Communicable command, CommandSender sender){

        //todo look for the similarities between playtiming while making this command
        return null;
    }

    /**
     * Blocks execution until {@link CommandExecutor#isIdle()} return's true.
     * @param commandExecutor a commandExecutor that could be executing tasks
     */
    public static void waitForExecutorToBeIdle(CommandExecutor commandExecutor) {
        //FIXME: this is horrible. a timed wait? we can do better.
        try {
            log.debug("Thread " + Thread.currentThread().getName() + " is waiting until Executor is finished with tasks");
            while (true){
                if (commandExecutor.isIdle()) {
                    break;
                } else {
                    Thread.sleep(50);
                }
            }
        } catch (InterruptedException e) {
            log.error("Exception occurred waiting for Executor to finish tasks", e);
        }
    }

    /**
     * Moves to the next phase after waiting for all existing commands to be finished.
     * @param context the context running the game
     * @param owner the owner of the next phase
     * @param nextPhase the phase to goto from the current one.
     * @param commandSender the sender of this command
     */
    public static void moveToNextPhase(GameContext context, String nextPhase, GamePlayer owner, CommandSender commandSender) {
        waitForExecutorToBeIdle(context.getCommandExecutor());
        //everyone made it through we can move on now
        Communicable nextPhaseCmd = wrapCommunicable(
                NextPhaseCommand.CMD_NAME, nextPhase, owner.getCommunicator().getID()
        );
        context.getGameCommunicationManager().decode(nextPhaseCmd, context.getCommandSender());
    }
}

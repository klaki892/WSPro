package ton.klay.wspro.core.game.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.LoseConditions;
import ton.klay.wspro.core.api.game.commands.CommandExecutionException;
import ton.klay.wspro.core.api.game.commands.CommandExecutor;
import ton.klay.wspro.core.api.game.commands.Command;
import ton.klay.wspro.core.api.game.commands.CommandSenderType;
import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.game.events.gameissued.GameEndEvent;
import ton.klay.wspro.core.game.events.gameissued.InvalidCommandEvent;

import java.util.Collections;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

public class WeissCommandExecutor implements CommandExecutor {

    private static final Logger log = LogManager.getLogger();
    private GameContext context;
    private final ExecutorService commandExecutor;
    private boolean isShutdownListenerActive;
    private PriorityBlockingQueue<Command> commandQueue;

    public WeissCommandExecutor(){
        //todo although this works, we should consider a way for us to maintain the queue (for later command injection into the queue?)
        commandQueue = new PriorityBlockingQueue<>(11, DefaultCommandComparator.obtain());

//        commandExecutor = Executors.newSingleThreadExecutor();
        commandExecutor = Executors.newCachedThreadPool();
//        commandExecutor = Executors.newFixedThreadPool(2);

        isShutdownListenerActive = false;


    }

    private final Runnable taskExecution = new Runnable() {
        @Override
        public void run() {
            try {
                Command cmd = commandQueue.take();
                WeissCommandExecutor.this.executeCommand(cmd);
            } catch (InterruptedException e) {
                log.warn("Command Executor was interrupted before finding a command to execute");
            }
        }
    };

    @Override
    public GameContext getGameContext() {
        return context;
    }

    @Override
    public void setGameContext(GameContext gameContext) {
        this.context = gameContext;

        if (!isShutdownListenerActive) {
            this.context.getEventManager().addEventListener(event -> {
                commandExecutor.shutdown();
                log.info("Shutting down Command Executor...");
                isShutdownListenerActive = true;
            }, GameEndEvent.EVENT_NAME);
        }

    }

    @Override
    public void issueCommand(Command command) {
        //todo create a new GameEndEvent Listener that will tell us to shut down our executor thread.
        //(if it hasnt been done already)


        //todo implement queue and sender checks
        log.trace("Received Command: " + command.getKeyword());
        if (!commandExecutor.isShutdown()) {

            commandQueue.put(command);
            commandExecutor.execute(taskExecution);
        }
    }

    @Override
    public boolean isIdle() {
        return commandQueue.isEmpty();
    }

    private void executeCommand(Command command){
        try {
            command.getExecution().execute(getGameContext(),command.getArgs(), command.getSender());
        } catch (InvalidCommandFromPlayerException e){
            log.warn("Received an invalid command from a player: " + e.getMessage());
            context.getEventManager().issueEvent(new InvalidCommandEvent(command));
        } catch (CommandExecutionException e) {
            if (command.getSender().getSenderType() == CommandSenderType.PLAYER){
                log.warn("Received an invalid command from a player", e);
                context.getEventManager().issueEvent(new InvalidCommandEvent(command));

                return;
            }
            log.fatal("error occurred with command: "  + command.getKeyword() + " from: " + command.getSender().getName(), e);
            //todo because we dont know where in the execution of the command failed, end the game.
            context.endGame(LoseConditions.GAME_ERROR, Collections.emptyList());
            commandExecutor.shutdownNow();
        } catch (RuntimeException e){
            log.fatal("error occurred outside of the command scope. Ending game", e);
            context.endGame(LoseConditions.GAME_ERROR, Collections.emptyList());
            commandExecutor.shutdownNow();
        }
    }
}

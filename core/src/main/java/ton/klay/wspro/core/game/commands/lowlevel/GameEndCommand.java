package ton.klay.wspro.core.game.commands.lowlevel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.LoseConditions;
import ton.klay.wspro.core.api.game.commands.CommandExecution;
import ton.klay.wspro.core.api.game.commands.CommandExecutionException;
import ton.klay.wspro.core.api.game.commands.CommandSender;
import ton.klay.wspro.core.api.game.communication.Communicable;
import ton.klay.wspro.core.api.game.phase.GamePhase;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.game.commands.BaseCommandExec;
import ton.klay.wspro.core.game.commands.CommandUtil;
import ton.klay.wspro.core.game.events.gameissued.GameEndEvent;
import ton.klay.wspro.core.game.events.gameissued.GameStartEvent;
import ton.klay.wspro.core.game.formats.standard.phases.TurnPhase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameEndCommand extends BaseCommandExec {


    public static final String CMD_NAME = "GAMEEND";


    public GameEndCommand() {}

    //GAMEEND [LOSE_CONDITION] [PLAYER_ID_LIST...]

    @Override
    public void execute(GameContext context, String[] args, CommandSender sender) throws CommandExecutionException {
        if (args.length >= 1) {

            LoseConditions loseCondition;
            try {
                loseCondition = LoseConditions.valueOf(args[0]);
            }catch (IllegalArgumentException iae){
                throw new CommandExecutionException(iae);
            }
            ArrayList<GamePlayer> losers = new ArrayList<>();
            for (int i = 1; i < args.length; i++) {
                GamePlayer player = CommandUtil.getPlayerFromID(context, args[i]);
                if (player != null) {
                    losers.add(player);
                } else {
                    throw new CommandExecutionException("Unknown Player ID Received: " + args[i]);
                }
            }
            context.endGame(loseCondition, losers);
            //game end event is issued from the context
        }
    }

    @Override
    public void undo(GameContext context, String[] args) {
        //cant undo the end of a game! (or can you)
    }
}


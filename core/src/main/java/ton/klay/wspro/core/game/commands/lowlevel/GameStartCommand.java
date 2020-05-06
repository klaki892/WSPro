package ton.klay.wspro.core.game.commands.lowlevel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.commands.Command;
import ton.klay.wspro.core.api.game.commands.CommandExecution;
import ton.klay.wspro.core.api.game.commands.CommandExecutionException;
import ton.klay.wspro.core.api.game.commands.CommandSender;
import ton.klay.wspro.core.api.game.communication.Communicable;
import ton.klay.wspro.core.api.game.phase.GamePhase;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.game.commands.CommandUtil;
import ton.klay.wspro.core.game.events.gameissued.GameStartEvent;
import ton.klay.wspro.core.game.formats.standard.phases.TurnPhase;

import java.util.*;

public class GameStartCommand implements CommandExecution {

    public static final String CMD_NAME = "GAMESTART";

    public GameStartCommand() {
    }

    // GAMESTART

    @Override
    public void execute(GameContext context, String[] args, CommandSender sender) throws CommandExecutionException {
        context.getEventManager().issueEvent(new GameStartEvent(context.getGameID()));

        GamePhase startPhase = context.getGameFormat().getPhase(TurnPhase.PREP_PHASE.name());

        //todo configuration to override who goes first?
        //randomly determine who goes first.
        List<GamePlayer> playerList = new ArrayList<>(context.getPlayers());
        Collections.shuffle(playerList, context.getRandom());

        Communicable command = CommandUtil.wrapCommunicable(NextPhaseCommand.CMD_NAME,
                startPhase.getPhaseName(), playerList.get(0).getCommunicator().getID());

        context.getGameCommunicationManager().decode(command, context.getCommandSender());
    }

    @Override
    public void undo(GameContext context, String[] args) {
        //cant undo the start of a game! (or can you)
    }

}

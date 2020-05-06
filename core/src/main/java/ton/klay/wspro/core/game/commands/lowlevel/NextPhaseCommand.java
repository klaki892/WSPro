package ton.klay.wspro.core.game.commands.lowlevel;

import ton.klay.wspro.core.api.game.commands.CommandExecutionException;
import ton.klay.wspro.core.api.game.commands.CommandSender;
import ton.klay.wspro.core.api.game.phase.GamePhase;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.game.commands.BaseCommandExec;
import ton.klay.wspro.core.game.commands.CommandUtil;
import ton.klay.wspro.core.game.events.gameissued.NextPhaseEvent;

public class NextPhaseCommand extends BaseCommandExec {

    public static final String CMD_NAME = "NEXTPHASE";
    public static final int ARG_COUNT = 2;

    public NextPhaseCommand() {}

    @Override
    public void execute(GameContext context, String[] args, CommandSender sender) throws CommandExecutionException {
        argCountCheck(ARG_COUNT, args, sender);

        //NEXTPHASE [PHASE_NAME] [PLAYER_ID]
        String phaseName = args[0];
        String playerId = args[1];

        //find the next phase
        GamePhase nextPhase = context.getGameFormat().getPhase(phaseName);
        if (nextPhase == null) {
            throw new CommandExecutionException("Phase was not found: " + phaseName);
        }

        //start the next phase with the owner:
        GamePlayer player;
        if ((player = CommandUtil.getPlayerFromID(context, playerId)) != null) {

            context.getEventManager().issueEvent(new NextPhaseEvent(nextPhase.getPhaseName(), playerId));
            nextPhase.startPhase(context, player);
        } else throw new CommandExecutionException("Unknown PlayerID received:" + playerId);
    }

    @Override
    public void undo(GameContext context, String[] args) {
        //cant undo the start of a game! (or can you)
    }

}

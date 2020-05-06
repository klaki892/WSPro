package ton.klay.wspro.core.game.commands.highlevel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.commands.CommandExecutionException;
import ton.klay.wspro.core.api.game.commands.CommandSender;
import ton.klay.wspro.core.api.game.communication.Communicable;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.game.commands.BaseCommandExec;
import ton.klay.wspro.core.game.commands.CommandUtil;
import ton.klay.wspro.core.game.commands.midlevel.MoveCardCommand;
import ton.klay.wspro.core.game.events.highlevel.DrawCardEvent;

public class DrawCardCommand extends BaseCommandExec {

    public static final String CMD_NAME = "DRAWCARD";
    public static final int ARG_COUNT = 1;

    GamePlayer player;

    //DRAWCARD [PLAYERID]

    @Override
    public void execute(GameContext context, String[] args, CommandSender sender) throws CommandExecutionException {
        argCountCheck(ARG_COUNT, args, sender);

        String playerID = args[0];

        player = CommandUtil.getPlayerFromID(context, playerID);
        if (player != null){
            //execute move command
            Communicable cmd = CommandUtil.wrapCommunicable(
                    MoveCardCommand.CMD_NAME, playerID, Zones.ZONE_DECK.name(), playerID, Zones.ZONE_HAND.name()
            );
            context.getGameCommunicationManager().decode(cmd, sender);
            context.getEventManager().issueEvent(new DrawCardEvent(playerID));
        } else
            throw new CommandExecutionException("invalid playerID given: " + playerID);
    }

    @Override
    public void undo(GameContext context, String[] args) throws CommandExecutionException {
        //todo
    }
}

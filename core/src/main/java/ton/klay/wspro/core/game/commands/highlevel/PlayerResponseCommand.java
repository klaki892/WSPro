package ton.klay.wspro.core.game.commands.highlevel;

import ton.klay.wspro.core.api.game.commands.CommandExecutionException;
import ton.klay.wspro.core.api.game.commands.CommandSender;
import ton.klay.wspro.core.api.game.communication.Communicable;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.game.commands.BaseCommandExec;
import ton.klay.wspro.core.game.commands.CommandUtil;
import ton.klay.wspro.core.game.commands.InvalidCommandFromPlayerException;
import ton.klay.wspro.core.game.commands.midlevel.MoveCardCommand;
import ton.klay.wspro.core.game.events.highlevel.DrawCardEvent;
import ton.klay.wspro.core.game.events.highlevel.PlayerResponseEvent;

public class PlayerResponseCommand extends BaseCommandExec {

    public static final String CMD_NAME = "RESPONSE";

    //RESPONSE [RESPONSEID] [ADDITIONAL ARGUMENTS]

    @Override
    public void execute(GameContext context, String[] args, CommandSender sender) throws CommandExecutionException {
        //Verify there is a responseID
        if (args.length <= 1)
            throw new InvalidCommandFromPlayerException("Response from player " + sender.getName() + " had  no ID, or no arguments");
        String responseID = args[0];

        //issue response event (just pass the args)
        context.getEventManager().issueEvent(new PlayerResponseEvent(args));

    }

    @Override
    public void undo(GameContext context, String[] args) throws CommandExecutionException {
        //CANT UNDO A PLAYER RESPONSE, WE CANT UNDO AN EVENT HAPPENING
    }
}

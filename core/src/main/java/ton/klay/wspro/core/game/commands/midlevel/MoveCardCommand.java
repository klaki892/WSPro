package ton.klay.wspro.core.game.commands.midlevel;

import ton.klay.wspro.core.api.game.cards.GameCard;
import ton.klay.wspro.core.api.game.commands.ArgumentCountError;
import ton.klay.wspro.core.api.game.commands.CommandExecutionException;
import ton.klay.wspro.core.api.game.commands.CommandSender;
import ton.klay.wspro.core.api.game.commands.CommandSenderType;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.game.commands.BaseCommandExec;
import ton.klay.wspro.core.game.commands.CommandUtil;
import ton.klay.wspro.core.game.commands.InvalidCommandFromPlayerException;
import ton.klay.wspro.core.game.events.midlevel.MoveCardEvent;

public class MoveCardCommand extends BaseCommandExec {


    public static final String CMD_NAME = "MOVE";

    //used for issuing redo's
    GamePlayer srcPlayer, dstPlayer;
    PlayZone srcZone, dstZone;
    String cardID;

    public MoveCardCommand(){}

    @Override
    public void execute(GameContext context, String[] args, CommandSender sender) throws CommandExecutionException {
        //check to make sure we have all arguments
        if (args.length >= 4 && args.length < 6) {

            //MOVE [SRC PLAYER ID] [SRC ZONE NAME] [DST PLAYER ID] [DST ZONE NAME] [(optional) CARD ID ]
            //resolve all arguments

            srcPlayer = CommandUtil.getPlayerFromID(context, args[0]);
            dstPlayer = CommandUtil.getPlayerFromID(context, args[2]);
            if (srcPlayer != null && dstPlayer != null) {
                try {
                    srcZone = srcPlayer.getPlayArea().getPlayZone(Zones.valueOf(args[1]));
                } catch (IllegalArgumentException iae) {
                    String message = "Unknown play area: " + args[1];
                    throw new CommandExecutionException(message);
                }

                try {
                    dstZone = dstPlayer.getPlayArea().getPlayZone(Zones.valueOf(args[3]));
                } catch (IllegalArgumentException iae) {
                    String message = "Unknown play area: " + args[3];
                        throw new CommandExecutionException(message);
                }

                GameCard card;
                if (args.length == 5) { //we have a specific card we are taking
                    cardID = args[4];
                    card = srcZone.getCard(cardID);
                } else { //pull the first card available from the zone
                    card = srcZone.getCard();
                }

                if (card != null){
                    srcZone.remove(card);
                    dstZone.add(card);
                } else {
                    throw new CommandExecutionException("Card " + cardID + " was not found in " + srcZone.getZoneName());
                }


                //issue move event
                context.getEventManager().issueEvent(new MoveCardEvent(args[0], args[1], args[2], args[3]));

            }
        } else {
            throw new ArgumentCountError(4 ,args.length);
        }

    }

    @Override
    public void undo(GameContext context, String[] args) throws CommandExecutionException {
        //todo undo movecommand
    }
}

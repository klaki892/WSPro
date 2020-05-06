package ton.klay.wspro.core.game.commands.midlevel;

import ton.klay.wspro.core.api.game.cards.GameCard;
import ton.klay.wspro.core.api.game.commands.CommandExecutionException;
import ton.klay.wspro.core.api.game.commands.CommandSender;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.game.commands.BaseCommandExec;
import ton.klay.wspro.core.game.commands.CommandUtil;
import ton.klay.wspro.core.api.game.commands.UndoCommandException;
import ton.klay.wspro.core.game.events.midlevel.ShuffleEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShuffleCommand extends BaseCommandExec {


    public static final String CMD_NAME = "SHUFFLE";
    public static final int ARG_COUNT = 2;
    //used for issuing redo's
    private List<GameCard> zoneState;
    private PlayZone undoZone;

    public ShuffleCommand(){}

    @Override
    public void execute(GameContext context, String[] args, CommandSender sender) throws CommandExecutionException {
        //check to make sure we have two arguments
        argCountCheck(ARG_COUNT, args, sender);

        //SHUFFLE [PLAYER_ID] [CARD ZONE NAME]

        String playerId = args[0];
        String zoneName = args[1];

        GamePlayer player = CommandUtil.getPlayerFromID(context, playerId);
        if (player == null) throw new CommandExecutionException("Couldn't get player from PlayerID");

        PlayZone zone;
        try {
            zone = player.getPlayArea().getPlayZone(Zones.valueOf(zoneName.trim()));
        } catch (IllegalArgumentException iae){
            throw new CommandExecutionException(iae);
        }

        if (!zone.isEmpty()) {
            List<GameCard> contents = zone.getContents();

            //save the state for an undo
            zoneState = new ArrayList<>(contents);
            undoZone = zone;

            //todo in the global GUID list, change the GUID for each card included in this shuffle.
            Collections.shuffle(contents, context.getRandom());

            context.getEventManager().issueEvent(new ShuffleEvent(playerId, zoneName));
        }


    }

    @Override
    public void undo(GameContext context, String[] args) throws CommandExecutionException {
        if (zoneState != null && zoneState.size() >= 1){
            undoZone.clear();
            for (GameCard card : zoneState ) {
                undoZone.add(card);
            }
        } else {
            throw new UndoCommandException("Cannot undo shuffle Zone: " + undoZone.toString() + " has no cards or is unknown");
        }
    }
}

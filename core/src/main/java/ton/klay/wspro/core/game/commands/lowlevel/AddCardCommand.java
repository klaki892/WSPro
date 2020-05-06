package ton.klay.wspro.core.game.commands.lowlevel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.Card;
import ton.klay.wspro.core.api.game.cards.GameCard;
import ton.klay.wspro.core.api.game.commands.CommandExecutionException;
import ton.klay.wspro.core.api.game.commands.CommandSender;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.phase.GamePhase;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.game.commands.BaseCommandExec;
import ton.klay.wspro.core.game.commands.CommandUtil;
import ton.klay.wspro.core.game.events.gameissued.NextPhaseEvent;
import ton.klay.wspro.core.game.formats.standard.cards.WeissCard;

public class AddCardCommand extends BaseCommandExec {

    private static final Logger log = LogManager.getLogger();


    public static final String CMD_NAME = "ADDCARD";
    public static final int ARG_COUNT = 3;


    public AddCardCommand() {}

    private GamePlayer player;
    private GameCard gameCard;
    private PlayZone zone;

    @Override
    public void execute(GameContext context, String[] args, CommandSender sender) throws CommandExecutionException {
        argCountCheck(ARG_COUNT, args, sender);

        //ADDCARD [PLAYER ID] [CARD ID] [ZONE NAME]

        String playerID= args[0];
        String cardID = args[1];
        String zoneName = args[2];

        player = CommandUtil.getPlayerFromID(context, playerID);
        if (player == null)
            throw new CommandExecutionException("Unknown PlayerID: " + playerID);

        try {
            zone = player.getPlayArea().getPlayZone(Zones.valueOf(zoneName));
        } catch (IllegalArgumentException iae){
            throw new CommandExecutionException("Unknown zone: " + zoneName);
        }


        for (Card card : player.getDeck().getCards()){
            if (card.getID().equals(cardID)){
                //create the new GameCard surrounding it
                log.trace("Initializing Card: " + card.getID());
                gameCard = new WeissCard(context, card, playerID);

                //add the card to the zone
                zone.add(gameCard);
                return;
            }
        }
        throw new CommandExecutionException("Card ID " + cardID + "was  not found in " + playerID + "'s deck:");



        //this command does not issue an event, too low level.

    }

    @Override
    public void undo(GameContext context, String[] args) {
        zone.remove(gameCard);
    }

}

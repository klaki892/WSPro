package ton.klay.wspro.core.game.commands.highlevel;

import ton.klay.wspro.core.api.cards.Card;
import ton.klay.wspro.core.api.game.GameRuntimeException;
import ton.klay.wspro.core.api.game.cards.GameCard;
import ton.klay.wspro.core.api.game.commands.*;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.game.commands.BaseCommandExec;
import ton.klay.wspro.core.game.commands.CommandUtil;
import ton.klay.wspro.core.game.events.highlevel.InfoResponseEvent;
import ton.klay.wspro.core.game.events.highlevel.PlaytimingEvent;
import ton.klay.wspro.core.game.formats.standard.phases.TurnPhase;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class InfoCommand extends BaseCommandExec {

    public static final String CMD_NAME = "INFO";
    public static final int MANDATORY_ARGS = 3;

    //INFO [REQUESTID] [INFO_TYPE] [KEYWORD ARGS...]


    @Override
    public void execute(GameContext context, String[] args, CommandSender sender) throws CommandExecutionException {
        //Verify the first 4 arguments exist
        if (args.length < MANDATORY_ARGS){
            throwEx("Info command didnt have at least "+ MANDATORY_ARGS + " args: " + Arrays.toString(args));
        }

        //check all mandataory arguments

        //requestID (cant really verify this)
        String requestID = args[0];
        String infoString = args[1];

        //INFO_TYPE (INFO type)
        InfoType infoType = null;
        try {
            infoType = InfoType.valueOf(infoString);
        } catch (IllegalArgumentException iae){
            throwEx("Unknown Info type: " + infoString);
        }

        Collection<String> infoResponseArgs = performInfoLookup(context, infoType, Arrays.asList(args).subList(2, args.length), sender);

        //all checks are done, make the event.
        //PLAYTIMING [PLAYER ID] [TimeStamp] [REQUESTID] | [PT_TYPE] [DECISION_TYPE /PHASE NAME] [DECISION_COUNT] [OPTION_TYPE] [OPTION LIST...]

        //INFORESPONSE [REQUESTID] [RESPONSE ARGS...]

        List<String> eventArgList = new ArrayList<>();
        eventArgList.add(requestID);
        eventArgList.addAll(infoResponseArgs);
        context.getEventManager().issueEvent(new InfoResponseEvent(eventArgList.toArray(new String[0])));

    }

    private Collection<String> performInfoLookup(GameContext context, InfoType infoType, List<String> infoArgs, CommandSender sender) throws CommandExecutionException {
        switch (infoType){
            case CARD:{

                break;
            }
            case ZONE:{
                return performZoneInfo(context, infoArgs, sender);
            }

            default:
                throw new GameRuntimeException("Received Info request which is known by enum but not coded for:" + infoType.name());
        }
        //should never be hit.
        return null;
    }

    private Collection<String> performZoneInfo(GameContext context, List<String> zoneArgs, CommandSender sender) throws CommandExecutionException {
        //ZONE [OWNERID] [ZONE_NAME] [# of cards in response] (foreach: [JSON OF GAMECARD DATA]
        try {
            String ownerID = zoneArgs.get(0);
            String zoneName = zoneArgs.get(1);

            //get the player
            GamePlayer player = CommandUtil.getPlayerFromID(context, ownerID);
            if (player == null) {
                throwEx("Unknown PlayerID");
            }

            //get the playzone
            PlayZone zone;
            try {
                zone = player.getPlayArea().getPlayZone(Zones.valueOf(zoneName));
            } catch (IllegalArgumentException iae){
                throw new CommandExecutionException(iae);
            }

            List<GameCard> contents = zone.getContents();
            for (GameCard card : contents){
                //todo finish this
            }


        } catch (IndexOutOfBoundsException boundEx){
            throw new CommandExecutionException("Not enough arguments for INFO ZONE command.", boundEx);
        }
        return null; //FIXME (temp placed here)
    }

    private void checkDecisionArguments(GameContext context, DecisionType decisionType, String[] args) throws CommandExecutionException {
        try {
            switch (decisionType){
                //[DECISION TYPE] [DECISION COUNT] [OPTION TYPE] [LIST OF ARGS...]
                case PICK:
                case UPTO:{
                    //check decision count]
                    int numDecisions = Integer.parseInt(args[InfoCommand.MANDATORY_ARGS]);
                    if (numDecisions <= 0) {
                        throwEx("Number of decision in playtiming command was not a positive number" + Arrays.toString(args));
                    }

                    //check the option type
                    String optionType = args[InfoCommand.MANDATORY_ARGS +1];

                    //todo dont be lazy? create an enum?
                    if (optionType.equalsIgnoreCase("SPECIAL")) {
                        //ensure there is at least 2 more options for the user to choose from
                        if ((InfoCommand.MANDATORY_ARGS + 3) >= args.length) {
                            throwEx("SPECIAL options needs at least 2 choices:" + Arrays.toString(args));
                        }
                        //all args passed!
                    } else if (optionType.equalsIgnoreCase("CARDS")) {

                        //check that there was at least one option
                        if ((InfoCommand.MANDATORY_ARGS + 2) >= args.length) {
                            throwEx("There were no card choices  in the playtiming (and Klayton doesnt know how to handle that yet): " + Arrays.toString(args));
                            //FIXME this is heavy handed, it is entirely possible that there's a case where a searchcontext returns 0 options and throws it into this command.
                            //we need a system where if there are no options...what do we do? the player cant make a decision!
                        }

                        //check that the list of options resolve to cards
                        for (int i = InfoCommand.MANDATORY_ARGS +2; i < args.length ; i++) {
                            boolean foundCard = false;
                            for (GamePlayer player : context.getPlayers()){
                                for (Card card : player.getDeck().getCards()){
                                    if (card.getID().equals(args[i])) {
                                        foundCard = true;
                                        break;
                                    }
                                }
                                if (foundCard) break;
                            }
                            if (!foundCard) {
                                throwEx("CardID didnt resolve to a known card for a decision. Args: " + Arrays.toString(args));
                            }
                        }
                        //all args passed!
                    } else {
                        throwEx("Unknown Option type: " + optionType);
                    }
                }break;

                case BOOLEAN:{
                    //there should be no more arguments
                    if (InfoCommand.MANDATORY_ARGS != args.length)
                        throwEx("Too many arguments for Boolean Playtiming. Recieved: " + Arrays.toString(args));
                }
            }
        } catch (IndexOutOfBoundsException ex) {
            throwEx("Not enough arguments for Playtiming: " + Arrays.toString(args));
        }
    }

    private void throwEx(String message) throws CommandExecutionException {
        throw new CommandExecutionException(message);
    }

    @Override
    public void undo(GameContext context, String[] args) throws CommandExecutionException {
        //TODO cancel the listener? its the best thing you could do
    }
}

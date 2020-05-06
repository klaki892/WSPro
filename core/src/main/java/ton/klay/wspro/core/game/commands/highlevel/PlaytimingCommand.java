package ton.klay.wspro.core.game.commands.highlevel;

import ton.klay.wspro.core.api.cards.Card;
import ton.klay.wspro.core.api.game.commands.CommandExecutionException;
import ton.klay.wspro.core.api.game.commands.CommandSender;
import ton.klay.wspro.core.api.game.commands.DecisionType;
import ton.klay.wspro.core.api.game.commands.PlayTimingType;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.game.commands.BaseCommandExec;
import ton.klay.wspro.core.game.commands.CommandUtil;
import ton.klay.wspro.core.game.events.highlevel.PlaytimingEvent;
import ton.klay.wspro.core.game.formats.standard.phases.TurnPhase;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlaytimingCommand extends BaseCommandExec {

    public static final String CMD_NAME = "PLAYTIMING";
    public static final int MANDATORY_ARGS = 4;

    //PLAYTIMING [PLAYER ID] [REQUESTID] | [PT_TYPE] [DECISION_TYPE /PHASE NAME] [DECISION_COUNT] [OPTION_TYPE] [OPTION LIST...]

    @Override
    public void execute(GameContext context, String[] args, CommandSender sender) throws CommandExecutionException {
        //Verify the first 4 arguments exist
        if (args.length < MANDATORY_ARGS){
            throwEx("Playtiming command didnt have at least 4 args: " + Arrays.toString(args));
        }

        //check all 4 arguments
        String playerID = args[0];
        if (CommandUtil.getPlayerFromID(context, playerID) == null)
            throwEx("player ID not found: " + playerID);

        //requestID (cant really verify this)
        String requestID = args[1];



        //timestamp for event
        String timeStamp = Long.toString(Instant.now(Clock.systemUTC()).toEpochMilli());

        //PT_TYPE (Play timing type)
        String ptTypeString = args[2];
        PlayTimingType ptType = null;
        try {
            ptType = PlayTimingType.valueOf(ptTypeString);
        } catch (IllegalArgumentException iae){
            throwEx("Unknown Play timing type: " + ptTypeString);
        }

        //decision type (or phase name)
        String decisionType = args[3];

        //check the decision type
        switch (ptType) {

            case PHASE:{
                //confirm the phase name
                try {
                    TurnPhase phaseName = TurnPhase.valueOf(decisionType);
                } catch (IllegalArgumentException iae){
                    throwEx("Unknown Turn phase type: " + decisionType);
                }
            } break;

            case DECISION: {
                //checks the decision type and the further options
                try {
                    DecisionType typeName = DecisionType.valueOf(decisionType);

                    checkDecisionArguments(context, typeName, args);
                } catch (IllegalArgumentException iae){
                    throwEx("Unknown Decision type: " + decisionType);
                }
            } break;

            default:
                throwEx("Unknown Play timing type: " + ptTypeString);
        }

        //all checks are done, make the event.
        //PLAYTIMING [PLAYER ID] [TimeStamp] [REQUESTID] | [PT_TYPE] [DECISION_TYPE /PHASE NAME] [DECISION_COUNT] [OPTION_TYPE] [OPTION LIST...]

        List<String> eventArgList = new ArrayList<>();
        eventArgList.add(playerID);
        eventArgList.add(requestID);
        eventArgList.add(timeStamp);
        eventArgList.addAll(Arrays.asList(args).subList(2, args.length));

        context.getEventManager().issueEvent(new PlaytimingEvent(eventArgList.toArray(new String[0])));

    }

    private void checkDecisionArguments(GameContext context, DecisionType decisionType, String[] args) throws CommandExecutionException {
        try {
            switch (decisionType){
                //[DECISION TYPE] [DECISION COUNT] [OPTION TYPE] [LIST OF ARGS...]
                case PICK:
                case UPTO:{
                    //check decision count]
                    int numDecisions = Integer.parseInt(args[PlaytimingCommand.MANDATORY_ARGS]);
                    if (numDecisions <= 0) {
                        throwEx("Number of decision in playtiming command was not a positive number" + Arrays.toString(args));
                    }

                    //check the option type
                    String optionType = args[PlaytimingCommand.MANDATORY_ARGS +1];

                    //todo dont be lazy? create an enum?
                    if (optionType.equalsIgnoreCase("SPECIAL")) {
                        //ensure there is at least 2 more options for the user to choose from
                        if ((PlaytimingCommand.MANDATORY_ARGS + 3) >= args.length) {
                            throwEx("SPECIAL options needs at least 2 choices:" + Arrays.toString(args));
                        }
                        //all args passed!
                    } else if (optionType.equalsIgnoreCase("CARDS")) {

                        //check that there was at least one option
                        if ((PlaytimingCommand.MANDATORY_ARGS + 2) >= args.length) {
                            throwEx("There were no card choices  in the playtiming (and Klayton doesnt know how to handle that yet): " + Arrays.toString(args));
                            //FIXME this is heavy handed, it is entirely possible that there's a case where a searchcontext returns 0 options and throws it into this command.
                            //we need a system where if there are no options...what do we do? the player cant make a decision!
                        }

                        //check that the list of options resolve to cards
                        for (int i = PlaytimingCommand.MANDATORY_ARGS +2; i < args.length ; i++) {
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
                    if (PlaytimingCommand.MANDATORY_ARGS != args.length)
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

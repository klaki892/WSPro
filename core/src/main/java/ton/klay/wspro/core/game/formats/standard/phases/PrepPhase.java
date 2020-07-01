package ton.klay.wspro.core.game.formats.standard.phases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.commands.DecisionType;
import ton.klay.wspro.core.api.game.commands.PlayTimingType;
import ton.klay.wspro.core.api.game.communication.Communicable;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.phase.GamePhase;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.game.commands.CommandUtil;
import ton.klay.wspro.core.game.commands.highlevel.PlaytimingCommand;
import ton.klay.wspro.core.game.commands.midlevel.MoveCardCommand;
import ton.klay.wspro.core.game.commands.highlevel.DrawCardCommand;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

import java.util.ArrayList;
import java.util.StringJoiner;

public class PrepPhase implements GamePhase {

    private static final Logger log = LogManager.getLogger();
    public static final String PHASE_NAME = TurnPhase.PREP_PHASE.name();
    private static final String mulliganResponseID = "mulliganResponseID";


    private static final int START_HAND_SIZE = 5; //change this to a gameconfig config?
    public PrepPhase(){}

    @Override
    public String getPhaseName() {
        return PHASE_NAME;
    }

    private void letPlayersMulligan(GameContext context, ArrayList<GamePlayer> players) {

        //handle the call to all players
        for (GamePlayer player : players) {

            //create the playtiming command
            //todo create a playtiming builder
            Communicable playTimingCommand = () -> {
                StringJoiner retString = new StringJoiner(" ");
                retString.add(PlaytimingCommand.CMD_NAME);
                retString.add(player.getCommunicator().getID());
                retString.add(mulliganResponseID); //create a random requestID?
                retString.add(PlayTimingType.DECISION.name());
                retString.add(DecisionType.UPTO.name());
                retString.add(Integer.toString(START_HAND_SIZE)); //default hand size
                retString.add("CARDS");
                for (PlayingCard handCard : player.getPlayArea().getPlayZone(Zones.ZONE_HAND).getContents()) {
                    retString.add(handCard.getID());
                }
                return retString.toString();
            };

            String[] cardSelection = CommandUtil.issuePlayTimingAndWait(context, playTimingCommand, context.getCommandSender());

            //remove the cards from hand and add an equal number back
            //MOVE [SRC PLAYER ID] [SRC ZONE NAME] [DST PLAYER ID] [DST ZONE NAME] [(optional) CARD ID ]
            for (String cardID : cardSelection) {
                Communicable moveCommand = CommandUtil.wrapCommunicable(
                        MoveCardCommand.CMD_NAME,
                        player.getCommunicator().getID(),
                        Zones.ZONE_HAND.name(),
                        player.getCommunicator().getID(),
                        Zones.ZONE_WAITING_ROOM.name(),
                        cardID
                );
                context.getGameCommunicationManager().decode(moveCommand, context.getCommandSender());
            }

            //draw cards equal to number of selection back
            for (int i = 0; i < cardSelection.length; i++){
                Communicable cmd = CommandUtil.wrapCommunicable(
                        DrawCardCommand.CMD_NAME,
                        player.getCommunicator().getID()
                );
                context.getGameCommunicationManager().decode(cmd, context.getCommandSender());
            }
        }
    }

}

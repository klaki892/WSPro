package ton.klay.wspro.core.game.actions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

/**
 * Utility functions which help operate the game. <br>
 * Some methods (namly all public ones) are exposed to the scripting engine to allow for effect creation
 * The rest (and ones excluded from mention in the scripting API) are meant to be internal handing functions
 */
public class DuelUtil {

    private static final Logger log = LogManager.getLogger();


    //general helper functions
    public static void checkIfWorked(boolean methodThatReturnsBoolean)throws InvalidActionException{
        if (!methodThatReturnsBoolean){
            throw new InvalidActionException("Action Failed");
        }
    }

    //effect triggerEvent headers
    private static final String underscore = "_";
    private static final String CONDITION_ = "CONDITION__";
    private static final String SENT_FROM = "SENT_FROM";





    /**
     * Send a card from it's existing location to a new location activating respecticve triggers along the way
     * @param card - card to be sent to new one
     * @param dZone - zone to recieve the card, provided it can
     * @return - if the operation was successful.
     */
    public static boolean SendCardToZone(PlayingCard card, PlayZone dZone){

        return false; //todo
    }


}

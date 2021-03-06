/*******************************************************************************
 *  WSPro - Trading Card Game Simulator
 *  Copyright (C) 2020  Klayton Killough
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published
 *  by the Free Software Foundation, version 3 of the License.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

package to.klay.wspro.core.game.actions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.zones.PlayZone;

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

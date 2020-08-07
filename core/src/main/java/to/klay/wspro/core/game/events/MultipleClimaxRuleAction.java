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

package to.klay.wspro.core.game.events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.CardOrientation;
import to.klay.wspro.core.api.game.field.Zones;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.commands.Commands;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import to.klay.wspro.core.game.formats.standard.zones.PlayZone;

/**
 * Handles the event where multiple characters are on the same stage position
 */
public class MultipleClimaxRuleAction implements RuleAction{

    private static final Logger log = LogManager.getLogger();
    private final GamePlayer player;

    public MultipleClimaxRuleAction(GamePlayer player) {

        this.player = player;
    }

    @Override
    public void execute() {
        //todo how do we handle the simultaneous case?

        PlayZone waiting = player.getPlayArea().getPlayZone(Zones.ZONE_WAITING_ROOM);
        PlayZone climaxZone = player.getPlayArea().getPlayZone(Zones.ZONE_CLIMAX);
        if (climaxZone.getContents().size() > 1){
            int allButTopAmount = climaxZone.getContents().size()-1;

            //put all but the last card to enter the stage into the waiting room
            for (PlayingCard card : Commands.Utilities.getBottomOfZoneCards(climaxZone, allButTopAmount)) {
                Commands.moveCard(card, climaxZone, waiting, Commands.Utilities.getTopOfZoneIndex(waiting),
                        CardOrientation.STAND, waiting.getVisibility(), TriggerCause.GAME_ACTION, player);
            }
        }
    }
}

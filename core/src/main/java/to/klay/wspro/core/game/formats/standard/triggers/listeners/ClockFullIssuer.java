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

package to.klay.wspro.core.game.formats.standard.triggers.listeners;

import com.google.common.eventbus.Subscribe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.game.field.Zones;
import to.klay.wspro.core.game.Game;
import to.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.CardMovedTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.ClockFullTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;

/**
 * Observes a game and issues a Trigger Condition whenver a player's clock has 7 or more cards.
 */
public class ClockFullIssuer extends BaseObserver {

    private static final Logger log = LogManager.getLogger();

    public ClockFullIssuer(Game game){
        super(game);
    }

    @Subscribe
    public void triggerReceived(CardMovedTrigger event) {
        if (event.getDestinationZone().getZoneName() == Zones.ZONE_CLOCK){
            if (event.getDestinationZone().size() >= 7 ){
                BaseTrigger trigger = new ClockFullTrigger(event.getDestinationZone().getMaster(),
                        TriggerCause.GAME_ACTION, event.getSourceZone());
                game.getTriggerManager().post(trigger);
                game.continuousTiming();
            }
        }
    }
}

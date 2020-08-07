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

package to.klay.wspro.core.game.formats.standard.triggers.listeners.ruleactions;

import com.google.common.eventbus.Subscribe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.Game;
import to.klay.wspro.core.game.events.RefreshPointRuleAction;
import to.klay.wspro.core.game.formats.standard.triggers.RefreshPointAddedTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.listeners.BaseObserver;

/**
 * Observes a game and issues a {@link RefreshPointAddedTrigger} if a player's deck is empty
 */
public class RefreshPointRuleIssuer extends BaseObserver {

    private static final Logger log = LogManager.getLogger();

    public RefreshPointRuleIssuer(Game game) {
        super(game);
    }

    @Subscribe
    public void triggerReceived(RefreshPointAddedTrigger trigger) {
        game.getTimingManager().add(new RefreshPointRuleAction(trigger.getPlayer()));

    }
}

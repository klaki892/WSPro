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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.Game;
import to.klay.wspro.core.game.formats.standard.triggers.listeners.ruleactions.LevelUpRuleIssuer;
import to.klay.wspro.core.game.formats.standard.triggers.listeners.ruleactions.LosingVerdictRuleIssuer;
import to.klay.wspro.core.game.formats.standard.triggers.listeners.ruleactions.MultipleCharactersRuleIssuer;
import to.klay.wspro.core.game.formats.standard.triggers.listeners.ruleactions.MultipleClimaxRuleIssuer;
import to.klay.wspro.core.game.formats.standard.triggers.listeners.ruleactions.RefreshPointRuleIssuer;
import to.klay.wspro.core.game.formats.standard.triggers.listeners.ruleactions.ReshuffleRuleIssuer;

/**
 * Contains the listeners for rule actions and other known game event triggers which are used in a Standard Weiss game
 */
public class StandardWeissTriggerObservers {

    private static final Logger log = LogManager.getLogger();

    public StandardWeissTriggerObservers(Game game){
        registerGameListeners(game);
    }

    /**
     * Register the listeners for rule actions and other known game event triggers
     */
    private void registerGameListeners(Game game) {
        //rule action listeners
        new LevelUpRuleIssuer(game);
        new ReshuffleRuleIssuer(game);
        new RefreshPointRuleIssuer(game);
        new LosingVerdictRuleIssuer(game);
        new MultipleCharactersRuleIssuer(game);
        new MultipleClimaxRuleIssuer(game);

        //event listeners
        new ClockFullIssuer(game);
        new DeckEmptyIssuer(game);
    }

}

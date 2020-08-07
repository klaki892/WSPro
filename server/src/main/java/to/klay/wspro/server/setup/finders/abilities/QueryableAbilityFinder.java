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

package to.klay.wspro.server.setup.finders.abilities;

import to.klay.wspro.core.api.cards.PaperCard;
import to.klay.wspro.core.game.AbilityFinder;

public interface QueryableAbilityFinder extends AbilityFinder  {

    /**
     * Reports if an ability list exists for a particular card
     * @param card the card to check for abilities
     * @return true if we could find the ability definitions for a card, false otherwise.
     */
    boolean doesScriptExist(PaperCard card);


}

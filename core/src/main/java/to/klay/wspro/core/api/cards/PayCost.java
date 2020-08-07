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

package to.klay.wspro.core.api.cards;

import to.klay.wspro.core.api.game.IllegalGameState;

/**
 * The Component of a {@link Cost} which performs the actions needed to fulfill a cost. <br/>
 * When a cost has been paid a corresponding
 * {@link to.klay.wspro.core.game.formats.standard.triggers.TriggerName#COST_PAID} should be emitted
 *
 */
public interface PayCost  {
    void execute() throws IllegalGameState;
}

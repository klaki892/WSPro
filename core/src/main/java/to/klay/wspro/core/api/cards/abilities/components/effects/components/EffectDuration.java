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

package to.klay.wspro.core.api.cards.abilities.components.effects.components;

/**
 * definitions of lengths that an effect may run for
 */
public enum EffectDuration {

    DURATION_END_OF_TURN,
    DURATION_END_OF_NEXT_TURN,
    DURATION_END_OF_OPPONENTS_TURN,
    DURATION_END_OF_OPPONENTS_NEXT_TURN,
    DURATION_GAME,
}

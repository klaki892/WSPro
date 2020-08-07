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

/**
 * The Affiliation of a particular card in the game
 */
public enum CardAffiliation {

    /**
     * A card affiliated to the Weiss side of the game (White wings on the frame of the card)<br><br/>
     *
     * @see  <code>Weiss Schwarz Rule 2.18.1.1 </code>
     */
    AFFILIATION_WEISS,

    /**
     * A card affiliated to the Weiss side of the game (Black wings on the frame of the card)<br><br/>
     *
     * @see <code>Weiss Schwarz Rule 2.18.1.2 </code>
     */
    AFFILIATION_SCHWARZ,

    /**
     * A card affiliated to the both sides (Weiss and Schwarz) side of the game (white and black wings on the frame of the card)<br><br/>
     *
     * @see  <code>Weiss Schwarz Rule 2.18.1</code>
     */
    AFFILIATION_BOTH

}

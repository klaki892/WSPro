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

package to.klay.wspro.core.api.game;

public enum LoseConditions {

    /**
     * The Lose Condition for a player hitting level 4 in their Level Zone.<br/><br/>
     * @see  <code>Weiss Schwarz Rule 1.2.2.1</code>
     */
    LEVEL4,
    /**
     * The Lose Condition for a player having no more cards in their deck or their waiting room<br/><br/>
     * @see  <code>Weiss Schwarz Rule 1.2.2.2</code>
     */
    EMPTY_DECK_AND_WAITING,

    /**
     * The lose condition for both player's losing at the same time. <br><br>
     *     Side note: Yes, it is possible. Specific case example is the effect from FT/EN-S02-037 U.
     * @see <code>Weiss Schwarz Rule 1.2.3</code>
     */
    DRAW_CONDITION,

    /**
     * The Lose Condition for a player conceding. (not technically a lose condition, but deal with it.)<br/><br/>
     * @see <code>Weiss Schwarz Rule 1.2.4</code>
     */
    CONCEDE,

    /**
     * The Lose Condition for a player losing by the effect of a card.<br/><br/>
     * @see <code>Weiss Schwarz Rule 1.2.5 </code>
     *
     */
    CARD_EFFECT_CONDITION,

    /**
     * A special lose condition to indicate the game was ended prematurely, no one should be a loser in this situation.
     */
    GAME_ERROR

}

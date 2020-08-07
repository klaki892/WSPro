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

package to.klay.wspro.core.api.game.setup;

/*
 * representation the of the Locale of the game/card <br/>
 * Can be used to optimize searches depending on which ruleset is being followed
 */
public enum  GameLocale {

    /**
     * The Japanense Locale. expect card names to be japanese and ban list of japan cards.
     */
    JP,

    /**
     * The English Locale. Expect card names to be in english and ban list of english cards
     */
    EN


}

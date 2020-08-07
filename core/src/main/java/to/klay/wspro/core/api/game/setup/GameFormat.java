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

import to.klay.wspro.core.api.game.DeckConstructionFormats;
import to.klay.wspro.core.api.game.phase.GamePhase;
import to.klay.wspro.core.api.game.player.GamePlayer;

import java.util.Collection;

/**
 * The Ruleset in which the game follows. <br/>
 * Provides a means of interacting with parameters of the game such as {@link GameLocale},
 * and {@link DeckConstructionFormats}
 */
public interface GameFormat {

    GameFormats getName();

    void setGameLocale(GameLocale gameLocale);

    GameLocale getGameLocale();

    /**
     * Returns true if all format restrictions have been met to start a game.
     *
     * @param players - A list of players to check that each one meets the game's requirements
     * @return True - restrictions have been met
     */
    boolean requirementsMet(Collection<GamePlayer> players);

    /**
     * Returns a list of errors that prevent the game from starting
     * due to restrictions on the game not being met.
     *
     * @return
     */
    Collection<String> getErrors();


    GamePhase getPhase(String phaseName);

    String getFormatInfo();


}

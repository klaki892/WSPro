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

package to.klay.wspro.core.game.cards.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseFilter implements CardFilter {

    private static final Logger log = LogManager.getLogger();

    @Override
    public List<PlayingCard> filter(List<PlayingCard> cards) {

        List<PlayingCard> list = new ArrayList<>();
        for (PlayingCard card : cards) {
            if (condition(card)) {
                list.add(card);
            }
        }
        return list;
    }

    /**
     * Condition to test a card on for passing the filter
     * @param card The card that will be tested
     * @return true if the card passes the filter. false otherwise
     */
    abstract boolean condition(PlayingCard card);
}

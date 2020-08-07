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

package to.klay.wspro.server.setup.finders.cards;

import to.klay.wspro.core.api.cards.PaperCard;

import java.util.List;
import java.util.Optional;

public interface CardFinder {

    Optional<PaperCard> getCard(String id);

    /**
     * Finds and creates cards based on ID, returning a mapping of the ID to the Papercard.
     * If ia Papercard could not be sourced, the mapping is to a null value.
     * @param cardIdList list of card ID's to check for corresponding paper cards
     * @return Map containing CardID -> Papercard or CardID -> null
     */
    CardFinderDeckResult sourceDeck(List<String> cardIdList);

}

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.PaperCard;

import java.util.List;

public class CardFinderDeckResult {

    private static final Logger log = LogManager.getLogger();
    private final List<PaperCard> foundCards;
    private final List<String> notFoundIds;

    private CardFinderDeckResult(List<PaperCard> foundCards, List<String> notFoundIds) {

        this.foundCards = foundCards;
        this.notFoundIds = notFoundIds;
    }

    public static CardFinderDeckResult create(List<PaperCard> foundCards, List<String> notFoundIds) {
        return new CardFinderDeckResult(foundCards, notFoundIds);
    }


    public List<PaperCard> getFoundCards() {
        return foundCards;
    }

    public List<String> getNotFoundIds() {
        return notFoundIds;
    }

    public boolean foundAllCards(){
        return notFoundIds.isEmpty();
    }
}

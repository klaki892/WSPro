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

package to.klay.wspro.server.game;

import to.klay.wspro.core.api.cards.PaperCard;
import to.klay.wspro.core.api.game.PaperDeck;

import java.util.List;

public class PaperDeckBuilder implements PaperDeck {
    private String deckName;
    private List<PaperCard> paperCards;

    private PaperDeckBuilder(String deckName, List<PaperCard> paperCards){

        this.deckName = deckName;
        this.paperCards = paperCards;
    }

    public static PaperDeckBuilder createPaperDeck(String deckName, List<PaperCard> paperCards) {
        return new PaperDeckBuilder(deckName, paperCards);
    }

    public PaperDeckBuilder setDeckName(String deckName) {
        this.deckName = deckName;
        return this;
    }

    public List<PaperCard> getCards() {
        return paperCards;
    }

    public PaperDeckBuilder setPaperCards(List<PaperCard> paperCards) {
        this.paperCards = paperCards;
        return this;
    }

    public String getDeckName() {
        return deckName;
    }

}
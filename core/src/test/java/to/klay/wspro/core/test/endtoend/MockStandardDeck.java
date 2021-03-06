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

package to.klay.wspro.core.test.endtoend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.PaperCard;
import to.klay.wspro.core.api.game.PaperDeck;
import to.klay.wspro.core.game.formats.standard.cards.MockCharacterPaperCard;
import to.klay.wspro.core.game.formats.standard.cards.MockClimaxPaperCard;

import java.util.ArrayList;
import java.util.Collection;

public class MockStandardDeck implements PaperDeck {

    private static final Logger log = LogManager.getLogger();
    Collection<PaperCard> cards = new ArrayList<>();

    public MockStandardDeck(){
        for (int i = 0; i < 42; i++)
            cards.add(new MockCharacterPaperCard());
        for (int i = 0; i < 8; i++)
            cards.add(new MockClimaxPaperCard());
    }

    @Override
    public String getDeckName() {
        return "Mock Standard deck";
    }

    @Override
    public Collection<PaperCard> getCards() {

        return cards;
    }
}

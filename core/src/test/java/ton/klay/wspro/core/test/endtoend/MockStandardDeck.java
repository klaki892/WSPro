package ton.klay.wspro.core.test.endtoend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.PaperCard;
import ton.klay.wspro.core.api.game.PaperDeck;
import ton.klay.wspro.core.game.formats.standard.cards.MockCharacterPaperCard;
import ton.klay.wspro.core.game.formats.standard.cards.MockClimaxPaperCard;

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

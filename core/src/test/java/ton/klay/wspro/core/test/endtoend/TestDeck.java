package ton.klay.wspro.core.test.endtoend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.IDeck;
import ton.klay.wspro.core.api.cards.PaperCard;

import java.util.ArrayList;
import java.util.Collection;

public class TestDeck implements IDeck {

    private static final Logger log = LogManager.getLogger();
    Collection<PaperCard> cards = new ArrayList<>();

    public TestDeck(){
        for (int i = 0; i < 50; i++)
            cards.add(new TestPaperCard());
    }

    @Override
    public String getName() {
        return "Test deck";
    }

    @Override
    public Collection<PaperCard> getCards() {
        //todo fill

        return cards;
    }
}

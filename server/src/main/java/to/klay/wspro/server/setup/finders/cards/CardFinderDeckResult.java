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

package ton.klay.wspro.core.api.game;

import ton.klay.wspro.core.api.cards.PaperCard;

import java.util.Collection;

public interface PaperDeck {
    String getDeckName();

    Collection<PaperCard> getCards();
}

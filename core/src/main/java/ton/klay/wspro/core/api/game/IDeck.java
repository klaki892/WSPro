package ton.klay.wspro.core.api.game;

import ton.klay.wspro.core.api.cards.Card;

import java.util.Collection;

public interface IDeck {
    String getName();

    Collection<Card> getCards();
}

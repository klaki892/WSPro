package to.klay.wspro.server;

import ton.klay.wspro.core.api.cards.PaperCard;

public interface CardFinder {

    PaperCard getCard(String id);
}

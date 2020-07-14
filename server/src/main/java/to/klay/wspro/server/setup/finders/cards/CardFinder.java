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

package to.klay.wspro.core.game.cards.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseFilter implements CardFilter {

    private static final Logger log = LogManager.getLogger();

    @Override
    public List<PlayingCard> filter(List<PlayingCard> cards) {

        List<PlayingCard> list = new ArrayList<>();
        for (PlayingCard card : cards) {
            if (condition(card)) {
                list.add(card);
            }
        }
        return list;
    }

    /**
     * Condition to test a card on for passing the filter
     * @param card The card that will be tested
     * @return true if the card passes the filter. false otherwise
     */
    abstract boolean condition(PlayingCard card);
}

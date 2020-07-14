package to.klay.wspro.core.game.cards.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;

public class SoulFilter extends NumericFilter {

    private static final Logger log = LogManager.getLogger();

    /**
     * Filters a list of cards for a specific soulCount
     * @param soulCount the number to be filtered
     */
    public SoulFilter(Operation operation, int soulCount){
        super(operation, soulCount);
    }

    @Override
    boolean condition(PlayingCard card) {
        return runNumericCondition(card.getSoul());
    }

}

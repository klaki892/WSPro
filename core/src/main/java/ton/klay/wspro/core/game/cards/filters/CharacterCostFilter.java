package ton.klay.wspro.core.game.cards.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

public class CharacterCostFilter extends NumericFilter {

    private static final Logger log = LogManager.getLogger();

    /**
     * Filters a list of cards for a specific cost
     * @param cost the number to be filtered
     */
    public CharacterCostFilter(Operation operation, int cost){
        super(operation, cost);
    }

    @Override
    boolean condition(PlayingCard card) {
        return runNumericCondition(card.getPaperCard().getCost());
    }
}

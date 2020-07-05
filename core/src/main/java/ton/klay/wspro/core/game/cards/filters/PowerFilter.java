package ton.klay.wspro.core.game.cards.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

public class PowerFilter extends NumericFilter {

    private static final Logger log = LogManager.getLogger();

    /**
     * Filters a list of cards for a specific power
     * @param power
     */
    public PowerFilter(Operation operation, int power){
        super(operation, power);
    }

    @Override
    boolean condition(PlayingCard card) {
        return runNumericCondition(card.getPower());
    }
}

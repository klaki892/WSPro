package to.klay.wspro.core.game.cards.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;

public class LevelFilter extends NumericFilter {

    private static final Logger log = LogManager.getLogger();

    /**
     * Filters a list of cards for a specific level
     * @param level
     */
    public LevelFilter(Operation operation, int level){
        super(operation, level);
    }

    @Override
    boolean condition(PlayingCard card) {
        return runNumericCondition(card.getLevel());
    }


}

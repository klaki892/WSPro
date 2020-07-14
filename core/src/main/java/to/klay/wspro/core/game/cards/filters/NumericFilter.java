package to.klay.wspro.core.game.cards.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.game.GameRuntimeException;

public abstract class NumericFilter extends BaseFilter {

    private static final Logger log = LogManager.getLogger();
    final Operation operation;
    final int criteriaNumber;

    NumericFilter(Operation operation, int criteriaNumber ){
        super();

        this.operation = operation;
        this.criteriaNumber = criteriaNumber;
    }


    public boolean runNumericCondition(int numberToBeFiltered){
        switch (operation) {
            case LESS_THAN:
                return numberToBeFiltered < criteriaNumber;
            case GREATER_THAN:
                return  numberToBeFiltered > criteriaNumber;
            case EQUAL_TO:
                return numberToBeFiltered == criteriaNumber;
            case LESS_THAN_OR_EQUAL:
                return numberToBeFiltered <= criteriaNumber;
            case GREATER_THAN_OR_EQUAL:
                return  numberToBeFiltered >= criteriaNumber;
            default:
                throw new GameRuntimeException(new IllegalArgumentException("Unknown Operation for Numeric Filter specified"));
        }
    }


    public enum Operation {
        LESS_THAN,
        GREATER_THAN,
        EQUAL_TO,
        LESS_THAN_OR_EQUAL,
        GREATER_THAN_OR_EQUAL,
    }
}

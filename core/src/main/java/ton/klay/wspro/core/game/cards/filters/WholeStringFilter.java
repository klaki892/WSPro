package ton.klay.wspro.core.game.cards.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.LocalizedString;

public abstract class WholeStringFilter extends BaseFilter {

    private static final Logger log = LogManager.getLogger();
    private final LocalizedString criteriaString;

    WholeStringFilter(LocalizedString criteriaString ){
        this.criteriaString = criteriaString;
    }


    public boolean runStringCondition(LocalizedString stringToBeFiltered){
        //todo compare both EN and JP seperately
        return stringToBeFiltered.getString().equalsIgnoreCase(criteriaString.getString());
    }

}

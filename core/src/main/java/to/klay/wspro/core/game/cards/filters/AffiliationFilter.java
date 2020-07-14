package to.klay.wspro.core.game.cards.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.CardAffiliation;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;

public class AffiliationFilter extends BaseFilter {

    private static final Logger log = LogManager.getLogger();
    private final CardAffiliation cardAffiliation;


    public AffiliationFilter(CardAffiliation cardAffiliation){
        this.cardAffiliation = cardAffiliation;
    }

    @Override
    boolean condition(PlayingCard card) {
        return card.getAffiliations() == cardAffiliation;
    }
}

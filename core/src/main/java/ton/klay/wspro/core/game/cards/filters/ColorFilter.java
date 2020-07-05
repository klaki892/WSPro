package ton.klay.wspro.core.game.cards.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.CardColor;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

public class ColorFilter extends BaseFilter {

    private static final Logger log = LogManager.getLogger();
    private final CardColor cardColor;


    public ColorFilter(CardColor cardColor){
        this.cardColor = cardColor;
    }
    
    @Override
    boolean condition(PlayingCard card) {
        return card.getColor() == cardColor;
    }
}

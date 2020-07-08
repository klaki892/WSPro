package ton.klay.wspro.core.game.cards.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.game.cards.CardType;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

public class CardTypeFilter extends BaseFilter {

    private static final Logger log = LogManager.getLogger();
    private final CardType cardType;


    public CardTypeFilter(CardType cardType){
        this.cardType = cardType;
    }

    @Override
    boolean condition(PlayingCard card) {
        return card.getCardType() == cardType;
    }
}

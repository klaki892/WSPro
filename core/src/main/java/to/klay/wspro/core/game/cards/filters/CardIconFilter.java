package to.klay.wspro.core.game.cards.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.CardIcon;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;

public class CardIconFilter extends BaseFilter {

    private static final Logger log = LogManager.getLogger();
    private final CardIcon cardIcon;


    public CardIconFilter(CardIcon cardIcon){
        this.cardIcon = cardIcon;
    }

    @Override
    boolean condition(PlayingCard card) {
        return card.getIcon() == cardIcon;
    }
}

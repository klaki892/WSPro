package ton.klay.wspro.core.game.cards.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.CardTrigger;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

public class TriggerIconFilter extends BaseFilter {

    private static final Logger log = LogManager.getLogger();
    private final CardTrigger cardTrigger;


    public TriggerIconFilter(CardTrigger cardTrigger){
        this.cardTrigger = cardTrigger;
    }


    @Override
    boolean condition(PlayingCard card) {
        return card.getTriggerIcons().stream()
                .anyMatch(cardTrigger1 -> cardTrigger1 == cardTrigger);
    }

}

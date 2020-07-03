package ton.klay.wspro.core.game.formats.standard.triggers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

public class CardEncoredTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    private final PlayingCard card;

    public CardEncoredTrigger(PlayingCard card, TriggerCause cause, GamePlayer caller) {
        super(cause, caller);
        this.card = card;
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.CARD_ENCORED;
    }

    public PlayingCard getCard() {
        return card;
    }
}

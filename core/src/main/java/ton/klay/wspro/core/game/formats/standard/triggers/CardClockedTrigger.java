package ton.klay.wspro.core.game.formats.standard.triggers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

public class CardClockedTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    private final GamePlayer turnPlayer;
    private final PlayingCard card;

    public CardClockedTrigger(GamePlayer turnPlayer, PlayingCard card, TriggerCause cause, GameEntity caller) {
        super(cause, caller);
        this.turnPlayer = turnPlayer;
        this.card = card;
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.CARD_CLOCKED;
    }

    public GamePlayer getTurnPlayer() {
        return turnPlayer;
    }

    public PlayingCard getCard() {
        return card;
    }
}

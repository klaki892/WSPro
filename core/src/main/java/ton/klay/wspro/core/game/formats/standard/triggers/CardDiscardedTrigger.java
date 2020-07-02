package ton.klay.wspro.core.game.formats.standard.triggers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

public class CardDiscardedTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    private final GamePlayer player;
    private final PlayingCard card;

    public CardDiscardedTrigger(GamePlayer player, PlayingCard card, TriggerCause cause, GameEntity caller) {

        super(cause, caller);
        this.player = player;
        this.card = card;
    }

    public GamePlayer getPlayer() {
        return player;
    }

    public PlayingCard getCard() {
        return card;
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.CARD_DISCARDED;
    }
}

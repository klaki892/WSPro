package ton.klay.wspro.core.game.formats.standard.triggers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

public class DamageProcessedTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    private final PlayingCard sourceCard;
    private final int amount;
    private final GamePlayer receivingPlayer;
    private final boolean wasCancelled;

    public DamageProcessedTrigger(PlayingCard sourceCard, int amount, GamePlayer receivingPlayer,
                                  boolean wasCancelled, TriggerCause cause, GameEntity caller) {
        super(cause, caller);
        this.sourceCard = sourceCard;
        this.amount = amount;
        this.receivingPlayer = receivingPlayer;
        this.wasCancelled = wasCancelled;
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.DAMAGE_PROCESSED;
    }

    public PlayingCard getSourceCard() {
        return sourceCard;
    }

    public int getAmount() {
        return amount;
    }

    public GamePlayer getReceivingPlayer() {
        return receivingPlayer;
    }

    public boolean isWasCancelled() {
        return wasCancelled;
    }
}

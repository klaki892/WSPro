package ton.klay.wspro.core.game.formats.standard.triggers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

public class TriggerCheckedTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    private final PlayingCard triggerCard;

    public TriggerCheckedTrigger(PlayingCard triggerCard, TriggerCause cause, GameEntity caller) {
        super(cause, caller);
        this.triggerCard = triggerCard;
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.TRIGGER_CARD_CHECKED;
    }

    public PlayingCard getTriggerCard() {
        return triggerCard;
    }
}

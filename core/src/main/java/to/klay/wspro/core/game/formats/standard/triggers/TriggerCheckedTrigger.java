package to.klay.wspro.core.game.formats.standard.triggers;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.game.GameEntity;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.proto.ProtoTriggerCheckedTrigger;
@ProtoClass(ProtoTriggerCheckedTrigger.class)
public class TriggerCheckedTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    @ProtoField
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

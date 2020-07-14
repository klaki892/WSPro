package ton.klay.wspro.core.game.formats.standard.triggers;

import net.badata.protobuf.converter.annotation.ProtoClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.proto.ProtoCostPaidTrigger;
import ton.klay.wspro.core.api.game.GameEntity;

/**
 * Announces that a cost for a card, ability, or effect was completed successfully.
 */
@ProtoClass(ProtoCostPaidTrigger.class)
public class CostPaidTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();

    public CostPaidTrigger(TriggerCause cause, GameEntity caller) {
        super(cause, caller);
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.COST_PAID;
    }
}

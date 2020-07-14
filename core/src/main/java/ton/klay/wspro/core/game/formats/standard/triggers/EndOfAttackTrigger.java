package ton.klay.wspro.core.game.formats.standard.triggers;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.proto.ProtoEndOfAttackTrigger;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.api.game.player.GamePlayer;

@ProtoClass(ProtoEndOfAttackTrigger.class)
public class EndOfAttackTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    @ProtoField
    private final GamePlayer turnPlayer;

    public EndOfAttackTrigger(GamePlayer turnPlayer, TriggerCause cause, GameEntity caller) {
        super(cause, caller);
        this.turnPlayer = turnPlayer;
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.END_OF_ATTACK;
    }

    public GamePlayer getTurnPlayer() {
        return turnPlayer;
    }
}

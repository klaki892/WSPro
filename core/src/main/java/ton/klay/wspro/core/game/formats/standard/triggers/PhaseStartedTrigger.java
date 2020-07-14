package ton.klay.wspro.core.game.formats.standard.triggers;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.proto.ProtoPhaseStartedTrigger;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.formats.standard.phases.TurnPhase;
import ton.klay.wspro.core.game.proto.TurnPhaseProtoTypeConverter;

@ProtoClass(ProtoPhaseStartedTrigger.class)
public class PhaseStartedTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    @ProtoField
    private final GamePlayer turnPlayer;
    @ProtoField(converter = TurnPhaseProtoTypeConverter.class)
    private final TurnPhase phase;

    public PhaseStartedTrigger(GamePlayer turnPlayer, TurnPhase phase, TriggerCause cause, GameEntity caller) {
        super(cause, caller);
        this.turnPlayer = turnPlayer;
        this.phase = phase;
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.PHASE_START;
    }

    public GamePlayer getTurnPlayer() {
        return turnPlayer;
    }

    public TurnPhase getPhase() {
        return phase;
    }
}

package to.klay.wspro.core.game.formats.standard.triggers;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.game.GameEntity;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.proto.ProtoTurnStartedTrigger;

@ProtoClass(ProtoTurnStartedTrigger.class)
public class TurnStartedTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    @ProtoField
    private final GamePlayer player;

    public TurnStartedTrigger(GamePlayer player, TriggerCause cause, GameEntity caller) {
        super(cause, caller);
        this.player = player;
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.TURN_STARTED;
    }

    public GamePlayer getPlayer() {
        return player;
    }
}

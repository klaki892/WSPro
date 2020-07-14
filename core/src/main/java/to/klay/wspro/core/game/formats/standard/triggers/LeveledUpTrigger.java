package to.klay.wspro.core.game.formats.standard.triggers;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.game.GameEntity;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.proto.ProtoLeveledUpTrigger;

@ProtoClass(ProtoLeveledUpTrigger.class)
public class LeveledUpTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    @ProtoField
    private final GamePlayer player;

    public LeveledUpTrigger(GamePlayer player, TriggerCause cause, GameEntity caller) {
        super(cause, caller);
        this.player = player;
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.LEVELED_UP;
    }

    public GamePlayer getPlayer() {
        return player;
    }
}

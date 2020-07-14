package ton.klay.wspro.core.game.formats.standard.triggers;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.proto.ProtoPlayerLostTrigger;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.api.game.LoseConditions;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.proto.LoseConditionsProtoTypeConverter;

@ProtoClass(ProtoPlayerLostTrigger.class)
public class PlayerLostTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    @ProtoField
    private final GamePlayer player;
    @ProtoField(converter = LoseConditionsProtoTypeConverter.class)
    private final LoseConditions loseCondition;

    public PlayerLostTrigger(GamePlayer player, LoseConditions loseCondition, TriggerCause cause, GameEntity caller) {
        super(cause, caller);
        this.player = player;
        this.loseCondition = loseCondition;
    }

    public GamePlayer getPlayer() {
        return player;
    }

    public LoseConditions getLoseCondition() {
        return loseCondition;
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.PLAYER_LOST;
    }
}

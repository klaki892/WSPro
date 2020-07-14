package ton.klay.wspro.core.game.formats.standard.triggers;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.proto.ProtoWillAttackTrigger;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.game.actions.AttackType;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.proto.AttackTypeProtoTypeConverter;

/**
 * indicates a player has decided to perform an attack with a particular character
 */
@ProtoClass(ProtoWillAttackTrigger.class)
public class WillAttackTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    @ProtoField
    private final PlayingCard attackingCard;
    @ProtoField(converter = AttackTypeProtoTypeConverter.class)
    private final AttackType attackType;

    public WillAttackTrigger(PlayingCard attackingCard, AttackType attackType, TriggerCause cause, GameEntity caller) {
    super(cause, caller);
        this.attackingCard = attackingCard;
        this.attackType = attackType;
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.WILL_ATTACK;
    }

    public PlayingCard getAttackingCard() {
        return attackingCard;
    }

    public AttackType getAttackType() {
        return attackType;
    }
}

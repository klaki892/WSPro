package to.klay.wspro.core.game.proto;

import net.badata.protobuf.converter.type.TypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.actions.AttackType;

public class AttackTypeProtoTypeConverter implements TypeConverter<AttackType, ProtoAttackType> {

    private static final Logger log = LogManager.getLogger();

    @Override
    public AttackType toDomainValue(Object instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProtoAttackType toProtobufValue(Object instance) {
        AttackType attackType = (AttackType) instance;
        return ProtoAttackType.forNumber(attackType.ordinal());
    }
}

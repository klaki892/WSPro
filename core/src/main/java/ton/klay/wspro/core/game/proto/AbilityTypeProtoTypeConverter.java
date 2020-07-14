package ton.klay.wspro.core.game.proto;

import net.badata.protobuf.converter.type.TypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.proto.ProtoAbilityType;
import ton.klay.wspro.core.api.cards.abilities.AbilityType;

public class AbilityTypeProtoTypeConverter implements TypeConverter<AbilityType, ProtoAbilityType> {

    private static final Logger log = LogManager.getLogger();

    @Override
    public AbilityType toDomainValue(Object instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProtoAbilityType toProtobufValue(Object instance) {
        AbilityType abilityType = (AbilityType) instance;
        return ProtoAbilityType.forNumber(abilityType.ordinal());
    }
}

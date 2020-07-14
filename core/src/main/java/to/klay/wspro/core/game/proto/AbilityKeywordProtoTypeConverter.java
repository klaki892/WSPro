package to.klay.wspro.core.game.proto;

import net.badata.protobuf.converter.type.TypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.abilities.AbilityKeyword;

public class AbilityKeywordProtoTypeConverter implements TypeConverter<AbilityKeyword, ProtoAbilityKeyword> {

    private static final Logger log = LogManager.getLogger();

    @Override
    public AbilityKeyword toDomainValue(Object instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProtoAbilityKeyword toProtobufValue(Object instance) {
        AbilityKeyword abilityKeyword = (AbilityKeyword) instance;
        return ProtoAbilityKeyword.forNumber(abilityKeyword.ordinal());
    }
}

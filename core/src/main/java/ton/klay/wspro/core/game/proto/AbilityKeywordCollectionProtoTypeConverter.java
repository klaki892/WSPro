package ton.klay.wspro.core.game.proto;

import net.badata.protobuf.converter.type.TypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.proto.ProtoAbilityKeyword;
import ton.klay.wspro.core.api.cards.abilities.AbilityKeyword;

import java.util.Collection;
import java.util.stream.Collectors;

public class AbilityKeywordCollectionProtoTypeConverter implements TypeConverter<Collection<AbilityKeyword>, Collection<ProtoAbilityKeyword>> {

    private static final Logger log = LogManager.getLogger();

    AbilityKeywordProtoTypeConverter protoTypeConverter = new AbilityKeywordProtoTypeConverter();

    @Override
    public Collection<AbilityKeyword> toDomainValue(Object instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<ProtoAbilityKeyword> toProtobufValue(Object instance) {
        Collection<AbilityKeyword> abilityKeywords = (Collection<AbilityKeyword>) instance;
        return abilityKeywords.stream().map(protoTypeConverter::toProtobufValue).collect(Collectors.toList());
    }
}

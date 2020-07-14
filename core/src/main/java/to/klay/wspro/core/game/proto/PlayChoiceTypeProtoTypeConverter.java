package to.klay.wspro.core.game.proto;

import net.badata.protobuf.converter.type.TypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.actions.PlayChoiceType;

public class PlayChoiceTypeProtoTypeConverter implements TypeConverter<PlayChoiceType, ProtoPlayChoiceType> {

    private static final Logger log = LogManager.getLogger();

    @Override
    public PlayChoiceType toDomainValue(Object instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProtoPlayChoiceType toProtobufValue(Object instance) {
        PlayChoiceType playChoiceType = (PlayChoiceType) instance;
        return ProtoPlayChoiceType.forNumber(playChoiceType.ordinal());
    }
}

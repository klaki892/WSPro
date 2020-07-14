package ton.klay.wspro.core.game.proto;

import net.badata.protobuf.converter.type.TypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.proto.ProtoLoseConditions;
import ton.klay.wspro.core.api.game.LoseConditions;

public class LoseConditionsProtoTypeConverter implements TypeConverter<LoseConditions, ProtoLoseConditions> {

    private static final Logger log = LogManager.getLogger();

    @Override
    public LoseConditions toDomainValue(Object instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProtoLoseConditions toProtobufValue(Object instance) {
        LoseConditions loseConditions = (LoseConditions) instance;
        return ProtoLoseConditions.forNumber(loseConditions.ordinal());
    }
}

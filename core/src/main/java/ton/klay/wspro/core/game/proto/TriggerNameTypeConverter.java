package ton.klay.wspro.core.game.proto;

import net.badata.protobuf.converter.type.TypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.proto.ProtoTriggerName;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerName;

public class TriggerNameTypeConverter implements TypeConverter<TriggerName, ProtoTriggerName> {

    private static final Logger log = LogManager.getLogger();

    @Override
    public TriggerName toDomainValue(Object instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProtoTriggerName toProtobufValue(Object instance) {
        TriggerName triggerName = (TriggerName) instance;
        return ProtoTriggerName.forNumber(triggerName.ordinal());
    }
}

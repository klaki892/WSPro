package ton.klay.wspro.core.game.proto;

import net.badata.protobuf.converter.type.TypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.proto.ProtoZones;
import ton.klay.wspro.core.api.game.field.Zones;

public class ZonesProtoTypeConverter implements TypeConverter<Zones, ProtoZones> {

    private static final Logger log = LogManager.getLogger();

    @Override
    public Zones toDomainValue(Object instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProtoZones toProtobufValue(Object instance) {
        Zones zones = (Zones) instance;
        return ProtoZones.forNumber(zones.ordinal());
    }
}

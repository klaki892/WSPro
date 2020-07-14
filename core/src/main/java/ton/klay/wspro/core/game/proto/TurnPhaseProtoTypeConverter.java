package ton.klay.wspro.core.game.proto;

import net.badata.protobuf.converter.type.TypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.proto.ProtoTurnPhase;
import ton.klay.wspro.core.game.formats.standard.phases.TurnPhase;

public class TurnPhaseProtoTypeConverter implements TypeConverter<TurnPhase, ProtoTurnPhase> {

    private static final Logger log = LogManager.getLogger();

    @Override
    public TurnPhase toDomainValue(Object instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProtoTurnPhase toProtobufValue(Object instance) {
        TurnPhase turnPhase = (TurnPhase) instance;
        return ProtoTurnPhase.forNumber(turnPhase.ordinal());
    }
}

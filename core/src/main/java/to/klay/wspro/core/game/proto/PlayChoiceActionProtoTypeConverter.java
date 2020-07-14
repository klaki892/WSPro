package to.klay.wspro.core.game.proto;

import net.badata.protobuf.converter.type.TypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.actions.PlayChoiceAction;

public class PlayChoiceActionProtoTypeConverter implements TypeConverter<PlayChoiceAction, ProtoPlayChoiceAction> {

    private static final Logger log = LogManager.getLogger();

    @Override
    public PlayChoiceAction toDomainValue(Object instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProtoPlayChoiceAction toProtobufValue(Object instance) {
        PlayChoiceAction PlayChoiceAction = (PlayChoiceAction) instance;
        return ProtoPlayChoiceAction.forNumber(PlayChoiceAction.ordinal());
    }
}

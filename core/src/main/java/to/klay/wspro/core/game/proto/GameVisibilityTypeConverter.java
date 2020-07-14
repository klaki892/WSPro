package to.klay.wspro.core.game.proto;

import net.badata.protobuf.converter.type.TypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.GameVisibility;

public class GameVisibilityTypeConverter implements TypeConverter<GameVisibility, ProtoGameVisibility> {

    private static final Logger log = LogManager.getLogger();

    @Override
    public GameVisibility toDomainValue(Object instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProtoGameVisibility toProtobufValue(Object instance) {
        GameVisibility gameVisibility = (GameVisibility) instance;
        return ProtoGameVisibility.forNumber(gameVisibility.ordinal());
    }
}

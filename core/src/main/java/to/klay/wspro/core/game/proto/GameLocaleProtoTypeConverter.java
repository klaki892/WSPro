package to.klay.wspro.core.game.proto;

import net.badata.protobuf.converter.type.TypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.game.setup.GameLocale;

public class GameLocaleProtoTypeConverter implements TypeConverter<GameLocale, ProtoGameLocale> {

    private static final Logger log = LogManager.getLogger();

    @Override
    public GameLocale toDomainValue(Object instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProtoGameLocale toProtobufValue(Object instance) {
        GameLocale gameLocale = (GameLocale) instance;
        return ProtoGameLocale.forNumber(gameLocale.ordinal());
    }
}

package to.klay.wspro.core.game.proto;

import net.badata.protobuf.converter.type.TypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.CardOrientation;

public class CardOrientationTypeConverter  implements TypeConverter<CardOrientation, ProtoCardOrientation> {

    private static final Logger log = LogManager.getLogger();

    @Override
    public CardOrientation toDomainValue(Object instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProtoCardOrientation toProtobufValue(Object instance) {
        CardOrientation cardOrientation = (CardOrientation) instance;
        return ProtoCardOrientation.forNumber(cardOrientation.ordinal());
    }
}

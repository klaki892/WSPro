package to.klay.wspro.core.game.proto;

import net.badata.protobuf.converter.type.TypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.CardIcon;

public class CardIconTypeConverter implements TypeConverter<CardIcon, ProtoCardIcon> {

    private static final Logger log = LogManager.getLogger();

    @Override
    public CardIcon toDomainValue(Object instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProtoCardIcon toProtobufValue(Object instance) {
        CardIcon cardIcon = (CardIcon) instance;
        return ProtoCardIcon.forNumber(cardIcon.ordinal());
    }
}

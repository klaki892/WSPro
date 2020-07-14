package to.klay.wspro.core.game.proto;

import net.badata.protobuf.converter.type.TypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.CardColor;

public class CardColorTypeConverter implements TypeConverter<CardColor, ProtoCardColor> {

    private static final Logger log = LogManager.getLogger();

    @Override
    public CardColor toDomainValue(Object instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProtoCardColor toProtobufValue(Object instance) {
        CardColor cardColor = (CardColor) instance;
        return ProtoCardColor.forNumber(cardColor.ordinal());
    }
}

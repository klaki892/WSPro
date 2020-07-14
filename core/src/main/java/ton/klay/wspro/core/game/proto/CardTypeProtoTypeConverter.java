package ton.klay.wspro.core.game.proto;

import net.badata.protobuf.converter.type.TypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.proto.ProtoCardType;
import ton.klay.wspro.core.game.cards.CardType;

public class CardTypeProtoTypeConverter implements TypeConverter<CardType, ProtoCardType> {

    private static final Logger log = LogManager.getLogger();

    @Override
    public CardType toDomainValue(Object instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProtoCardType toProtobufValue(Object instance) {
        CardType cardType = (CardType) instance;
        return ProtoCardType.forNumber(cardType.ordinal());
    }
}

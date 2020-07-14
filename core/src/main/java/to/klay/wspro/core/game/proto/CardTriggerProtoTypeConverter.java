package to.klay.wspro.core.game.proto;

import net.badata.protobuf.converter.type.TypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.CardTrigger;

public class CardTriggerProtoTypeConverter implements TypeConverter<CardTrigger, ProtoCardTrigger> {

    private static final Logger log = LogManager.getLogger();

    @Override
    public CardTrigger toDomainValue(Object instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProtoCardTrigger toProtobufValue(Object instance) {
        CardTrigger cardTrigger = (CardTrigger) instance;
        return ProtoCardTrigger.forNumber(cardTrigger.ordinal());
    }
}

package to.klay.wspro.core.game.proto;

import net.badata.protobuf.converter.type.TypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.CardAffiliation;

public class CardAffiliationProtoTypeConverter implements TypeConverter<CardAffiliation, ProtoCardAffiliation> {

    private static final Logger log = LogManager.getLogger();

    @Override
    public CardAffiliation toDomainValue(Object instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProtoCardAffiliation toProtobufValue(Object instance) {
        CardAffiliation cardAffiliation = (CardAffiliation) instance;
        return ProtoCardAffiliation.forNumber(cardAffiliation.ordinal());
    }
}

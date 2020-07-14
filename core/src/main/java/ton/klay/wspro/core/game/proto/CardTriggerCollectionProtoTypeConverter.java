package ton.klay.wspro.core.game.proto;

import net.badata.protobuf.converter.type.TypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.proto.ProtoCardTrigger;
import ton.klay.wspro.core.api.cards.CardTrigger;

import java.util.Collection;
import java.util.stream.Collectors;

public class CardTriggerCollectionProtoTypeConverter implements TypeConverter<Collection<CardTrigger>, Collection<ProtoCardTrigger>> {

    private static final Logger log = LogManager.getLogger();

    CardTriggerProtoTypeConverter protoTypeConverter = new CardTriggerProtoTypeConverter();

    @Override
    public Collection<CardTrigger> toDomainValue(Object instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<ProtoCardTrigger> toProtobufValue(Object instance) {
        Collection<CardTrigger> cardTriggers = (Collection<CardTrigger>) instance;
        return cardTriggers.stream().map(protoTypeConverter::toProtobufValue).collect(Collectors.toList());
    }
}

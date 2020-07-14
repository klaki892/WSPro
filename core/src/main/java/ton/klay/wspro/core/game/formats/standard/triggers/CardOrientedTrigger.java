package ton.klay.wspro.core.game.formats.standard.triggers;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.proto.ProtoCardOrientedTrigger;
import ton.klay.wspro.core.api.cards.CardOrientation;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.proto.CardOrientationTypeConverter;

@ProtoClass(ProtoCardOrientedTrigger.class)
public class CardOrientedTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    @ProtoField
    private final PlayingCard card;
    @ProtoField(converter = CardOrientationTypeConverter.class)
    private final CardOrientation orientedFrom;
    @ProtoField(converter = CardOrientationTypeConverter.class)
    private final CardOrientation orientTo;

    public CardOrientedTrigger(PlayingCard card, CardOrientation orientedFrom, CardOrientation orientTo, TriggerCause cause, GameEntity caller) {
        super(cause, caller);
        this.card = card;
        this.orientedFrom = orientedFrom;
        this.orientTo = orientTo;
    }

    public PlayingCard getCard() {
        return card;
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.CARD_ORIENTED;
    }

    public CardOrientation getOrientedFrom() {
        return orientedFrom;
    }

    public CardOrientation getOrientTo() {
        return orientTo;
    }
}

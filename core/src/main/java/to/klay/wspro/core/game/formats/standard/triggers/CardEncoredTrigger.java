package to.klay.wspro.core.game.formats.standard.triggers;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.proto.ProtoCardEncoredTrigger;

@ProtoClass(ProtoCardEncoredTrigger.class)
public class CardEncoredTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    @ProtoField
    private final PlayingCard card;

    public CardEncoredTrigger(PlayingCard card, TriggerCause cause, GamePlayer caller) {
        super(cause, caller);
        this.card = card;
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.CARD_ENCORED;
    }

    public PlayingCard getCard() {
        return card;
    }
}

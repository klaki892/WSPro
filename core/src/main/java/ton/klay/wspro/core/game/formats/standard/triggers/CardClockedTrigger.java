package ton.klay.wspro.core.game.formats.standard.triggers;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.proto.ProtoCardClockedTrigger;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

@ProtoClass(ProtoCardClockedTrigger.class)
public class CardClockedTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    @ProtoField
    private final GamePlayer player;
    @ProtoField
    private final PlayingCard card;

    public CardClockedTrigger(GamePlayer player, PlayingCard card, TriggerCause cause, GameEntity caller) {
        super(cause, caller);
        this.player = player;
        this.card = card;
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.CARD_CLOCKED;
    }

    public GamePlayer getPlayer() {
        return player;
    }

    public PlayingCard getCard() {
        return card;
    }

//    @Override
//    public GameMessageProto serializeToProto() {
//        ProtoCardClockedTrigger t =
//                Converter.create().toProtobuf(ProtoCardClockedTrigger.class, this);
//
//        return GameMessageProto.newBuilder()
//                .setTrigger(GameTriggerProto.newBuilder()
//                        .setCardClockedTrigger(t)
//                        .build())
//                .build();
//    }
}

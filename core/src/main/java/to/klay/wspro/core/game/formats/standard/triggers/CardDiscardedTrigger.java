package to.klay.wspro.core.game.formats.standard.triggers;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.game.GameEntity;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.proto.ProtoCardDiscardedTrigger;
@ProtoClass(ProtoCardDiscardedTrigger.class)
public class CardDiscardedTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    @ProtoField
    private final GamePlayer player;
    @ProtoField
    private final PlayingCard card;

    public CardDiscardedTrigger(GamePlayer player, PlayingCard card, TriggerCause cause, GameEntity caller) {

        super(cause, caller);
        this.player = player;
        this.card = card;
    }

    public GamePlayer getPlayer() {
        return player;
    }

    public PlayingCard getCard() {
        return card;
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.CARD_DISCARDED;
    }
}

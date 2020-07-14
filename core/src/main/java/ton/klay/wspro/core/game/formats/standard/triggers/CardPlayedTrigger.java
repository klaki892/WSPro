package ton.klay.wspro.core.game.formats.standard.triggers;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.proto.ProtoCardPlayedTrigger;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.zones.PlayZone;

@ProtoClass(ProtoCardPlayedTrigger.class)
public class CardPlayedTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    @ProtoField
    private final GamePlayer player;
    @ProtoField
    private final PlayingCard card;
    @ProtoField
    private final PlayZone sourceZone;
    @ProtoField
    private final PlayZone destinationZone;

    public CardPlayedTrigger(GamePlayer player, PlayingCard card, PlayZone sourceZone, PlayZone destinationZone, TriggerCause cause, GameEntity caller) {
        super(cause, caller);
        this.player = player;
        this.card = card;
        this.sourceZone = sourceZone;
        this.destinationZone = destinationZone;
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.CARD_PLAYED;
    }

    public GamePlayer getPlayer() {
        return player;
    }

    public PlayingCard getCard() {
        return card;
    }

    public PlayZone getSourceZone() {
        return sourceZone;
    }

    public PlayZone getDestinationZone() {
        return destinationZone;
    }
}

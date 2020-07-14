package ton.klay.wspro.core.game.formats.standard.triggers;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.proto.ProtoZoneShuffledTrigger;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.zones.PlayZone;
import ton.klay.wspro.core.game.proto.TriggerNameTypeConverter;

import java.util.List;

@ProtoClass(ProtoZoneShuffledTrigger.class)
public final class ZoneShuffledTrigger extends BaseTrigger{

    private static final Logger log = LogManager.getLogger();
    @ProtoField
    private final PlayZone zone;
    @ProtoField
    private final List<PlayingCard> cards;
    @ProtoField(converter = TriggerNameTypeConverter.class)
    private final TriggerName triggerName = TriggerName.ZONE_SHUFFLED;

    public ZoneShuffledTrigger(PlayZone zone, List<PlayingCard> cards, TriggerCause cause, GameEntity caller) {

        super(cause, caller);
        this.zone = zone;
        this.cards = cards;
    }


    @Override
    public TriggerName getTriggerName() {
        return triggerName;
    }

    public PlayZone getZone() {
        return zone;
    }

    public List<PlayingCard> getCards() {
        return cards;
    }

//    @Override
//    public GameMessageProto serializeToProto() {
//        GameTriggerProto triggerProto = GameTriggerProto.newBuilder()
//                .setZoneShuffledTrigger(
//                        Converter.create().toProtobuf(ProtoZoneShuffledTrigger.class, this)
//                ).build();
//        return GameMessageProto.newBuilder().setTrigger(triggerProto).build();
//    }
}

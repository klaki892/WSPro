package to.klay.wspro.core.game.formats.standard.triggers;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.CardOrientation;
import to.klay.wspro.core.api.cards.GameVisibility;
import to.klay.wspro.core.api.game.GameEntity;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.zones.PlayZone;
import to.klay.wspro.core.game.proto.CardOrientationTypeConverter;
import to.klay.wspro.core.game.proto.GameVisibilityTypeConverter;
import to.klay.wspro.core.game.proto.ProtoCardMovedTrigger;

/**
 * Announces that a card moved from a zone to another zone.
 */
@ProtoClass(ProtoCardMovedTrigger.class)
public final class CardMovedTrigger extends BaseTrigger{

    private static final Logger log = LogManager.getLogger();
    private final TriggerName name = TriggerName.CARD_MOVED;

    @ProtoField
    private final PlayingCard sourceCard;
    @ProtoField
    private final PlayZone sourceZone;
    @ProtoField
    private final PlayZone destinationZone;
    @ProtoField
    private final int destinationIndex;
    @ProtoField
    private final PlayingCard destinationCard;
    @ProtoField(converter = CardOrientationTypeConverter.class)
    private final CardOrientation destinationOrientation;
    @ProtoField(converter = GameVisibilityTypeConverter.class)
    private final GameVisibility destinationVisibility;

    public CardMovedTrigger(PlayingCard sourceCard, PlayZone sourceZone,
                            PlayZone destinationZone, int destinationIndex, PlayingCard destinationCard,
                            CardOrientation destinationOrientation, GameVisibility destinationVisibility,
                            TriggerCause cause, GameEntity owner){

        super(cause, owner);
        this.sourceCard = sourceCard;
        this.sourceZone = sourceZone;
        this.destinationZone = destinationZone;
        this.destinationIndex = destinationIndex;
        this.destinationCard = destinationCard;
        this.destinationOrientation = destinationOrientation;
        this.destinationVisibility = destinationVisibility;
    }

    public static Logger getLog() {
        return log;
    }

    public PlayingCard getSourceCard() {
        return sourceCard;
    }

    public PlayZone getSourceZone() {
        return sourceZone;
    }

    public PlayZone getDestinationZone() {
        return destinationZone;
    }

    public int getDestinationIndex() {
        return destinationIndex;
    }

    public PlayingCard getDestinationCard() {
        return destinationCard;
    }

    public CardOrientation getDestinationOrientation() {
        return destinationOrientation;
    }

    public GameVisibility getDestinationVisibility() {
        return destinationVisibility;
    }

    public TriggerName getTriggerName() {
        return name;
    }
}

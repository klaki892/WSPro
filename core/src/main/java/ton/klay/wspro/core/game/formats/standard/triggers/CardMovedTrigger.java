package ton.klay.wspro.core.game.formats.standard.triggers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.CardOrientation;
import ton.klay.wspro.core.api.cards.GameVisibility;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.zones.PlayZone;

/**
 * Announces that a card moved from a zone to another zone.
 */
public final class CardMovedTrigger extends BaseTrigger{

    private static final Logger log = LogManager.getLogger();
    private final TriggerName name = TriggerName.CARD_MOVED;

    private final PlayingCard sourceCard;
    private final PlayZone sourceZone;
    private final PlayZone destinationZone;
    private final int destinationIndex;
    private final PlayingCard destinationCard;
    private final CardOrientation destinationOrientation;
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

package ton.klay.wspro.core.game.formats.standard.triggers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.CardOrientation;
import ton.klay.wspro.core.api.game.Ownable;
import ton.klay.wspro.core.api.game.cards.GameVisibility;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

/**
 * Announces that a card moved from a zone to another zone.
 */
public class CardMovedTrigger extends BaseTrigger{

    private static final Logger log = LogManager.getLogger();

    private final PlayingCard sourceCard;
    private final PlayZone sourceZone;
    private final PlayZone destinationZone;
    private final int destinationIndex;
    private final PlayingCard destinationCard;
    private final CardOrientation destinationOrientation;
    private final GameVisibility destinationVisibility;
    private final TriggerCause cause;
    private final Ownable owner;

    public CardMovedTrigger(PlayingCard sourceCard, PlayZone sourceZone,
                            PlayZone destinationZone, int destinationIndex, PlayingCard destinationCard,
                            CardOrientation destinationOrientation, GameVisibility destinationVisibility,
                            TriggerCause cause, Ownable owner){

        this.sourceCard = sourceCard;
        this.sourceZone = sourceZone;
        this.destinationZone = destinationZone;
        this.destinationIndex = destinationIndex;
        this.destinationCard = destinationCard;
        this.destinationOrientation = destinationOrientation;
        this.destinationVisibility = destinationVisibility;
        this.cause = cause;
        this.owner = owner;
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

    public TriggerCause getCause() {
        return cause;
    }

    public Ownable getOwner() {
        return owner;
    }
}

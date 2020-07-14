package ton.klay.wspro.core.game.formats.standard.triggers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.zones.PlayZone;

public class CardPlayedTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    private final GamePlayer player;
    private final PlayingCard card;
    private final PlayZone sourceZone;
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

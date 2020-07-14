package ton.klay.wspro.core.game.formats.standard.triggers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.zones.PlayZone;

import java.util.List;

public final class ZoneShuffledTrigger extends BaseTrigger{

    private static final Logger log = LogManager.getLogger();
    private final PlayZone zone;
    private final List<PlayingCard> cards;
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
}

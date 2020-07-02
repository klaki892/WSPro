package ton.klay.wspro.core.game.formats.standard.triggers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

public final class CardDrawnTrigger extends BaseTrigger{

    private static final Logger log = LogManager.getLogger();
    private final GamePlayer player;
    private final PlayingCard card;

    public CardDrawnTrigger(GamePlayer player, PlayingCard drawnCard, TriggerCause cause, GameEntity caller) {

        super(cause, caller);
        this.player = player;
        card = drawnCard;
    }


    @Override
    public TriggerName getTriggerName() {
        return TriggerName.CARD_DRAWN;
    }

    public GamePlayer getPlayer() {
        return player;
    }

    public PlayingCard getCard() {
        return card;
    }
}

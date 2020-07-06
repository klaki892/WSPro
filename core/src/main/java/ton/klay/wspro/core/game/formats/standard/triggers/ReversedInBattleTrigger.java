package ton.klay.wspro.core.game.formats.standard.triggers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

public class ReversedInBattleTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    private final PlayingCard reversedCard;
    private final PlayingCard opposingCard;

    public ReversedInBattleTrigger(PlayingCard reversedCard, PlayingCard opposingCard, TriggerCause cause, GameEntity caller) {
    super(cause, caller);
        this.reversedCard = reversedCard;
        this.opposingCard = opposingCard;
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.REVERSED_IN_BATTLE;
    }

    public PlayingCard getReversedCard() {
        return reversedCard;
    }

    public PlayingCard getOpposingCard() {
        return opposingCard;
    }
}

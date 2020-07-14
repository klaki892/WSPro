package to.klay.wspro.core.game.formats.standard.triggers;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.game.GameEntity;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.proto.ProtoReversedInBattleTrigger;

@ProtoClass(ProtoReversedInBattleTrigger.class)
public class ReversedInBattleTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    @ProtoField
    private final PlayingCard reversedCard;
    @ProtoField
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

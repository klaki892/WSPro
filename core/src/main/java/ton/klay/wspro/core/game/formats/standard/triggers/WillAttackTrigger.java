package ton.klay.wspro.core.game.formats.standard.triggers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.game.actions.AttackType;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

/**
 * indicates a player has decided to perform an attack with a particular character
 */
public class WillAttackTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    private final PlayingCard attackingCard;
    private final AttackType attackType;

    public WillAttackTrigger(PlayingCard attackingCard, AttackType attackType, TriggerCause cause, GameEntity caller) {
    super(cause, caller);
        this.attackingCard = attackingCard;
        this.attackType = attackType;
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.WILL_ATTACK;
    }

    public PlayingCard getAttackingCard() {
        return attackingCard;
    }

    public AttackType getAttackType() {
        return attackType;
    }
}

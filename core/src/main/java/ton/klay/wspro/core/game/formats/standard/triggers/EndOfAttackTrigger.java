package ton.klay.wspro.core.game.formats.standard.triggers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.api.game.player.GamePlayer;

public class EndOfAttackTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    private final GamePlayer turnPlayer;

    public EndOfAttackTrigger(GamePlayer turnPlayer, TriggerCause cause, GameEntity caller) {
        super(cause, caller);
        this.turnPlayer = turnPlayer;
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.END_OF_ATTACK;
    }

    public GamePlayer getTurnPlayer() {
        return turnPlayer;
    }
}

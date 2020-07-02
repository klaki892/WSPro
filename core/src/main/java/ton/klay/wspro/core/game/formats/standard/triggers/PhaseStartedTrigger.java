package ton.klay.wspro.core.game.formats.standard.triggers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.formats.standard.phases.TurnPhase;

public class PhaseStartedTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    private final GamePlayer turnPlayer;
    private final TurnPhase identifier;

    public PhaseStartedTrigger(GamePlayer turnPlayer, TurnPhase phase, TriggerCause cause, GameEntity caller) {
        super(cause, caller);
        this.turnPlayer = turnPlayer;
        this.identifier = phase;
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.PHASE_START;
    }

    public GamePlayer getTurnPlayer() {
        return turnPlayer;
    }

    public TurnPhase getPhase() {
        return identifier;
    }
}

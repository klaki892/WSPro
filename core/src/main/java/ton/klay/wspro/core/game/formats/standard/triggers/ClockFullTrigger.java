package ton.klay.wspro.core.game.formats.standard.triggers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.api.game.player.GamePlayer;

public class ClockFullTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    private final GamePlayer player;

    public ClockFullTrigger(GamePlayer player, TriggerCause cause, GameEntity caller) {
        super(cause, caller);
        this.player = player;
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.CLOCK_FULL;
    }

    public GamePlayer getPlayer() {
        return player;
    }
}

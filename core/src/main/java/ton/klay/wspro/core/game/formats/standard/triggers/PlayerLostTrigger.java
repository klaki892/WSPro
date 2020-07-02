package ton.klay.wspro.core.game.formats.standard.triggers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.api.game.LoseConditions;
import ton.klay.wspro.core.api.game.player.GamePlayer;

public class PlayerLostTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    private final GamePlayer player;
    private final LoseConditions loseCondition;

    public PlayerLostTrigger(GamePlayer player, LoseConditions loseCondition, TriggerCause cause, GameEntity caller) {
        super(cause, caller);
        this.player = player;
        this.loseCondition = loseCondition;
    }

    public GamePlayer getPlayer() {
        return player;
    }

    public LoseConditions getLoseCondition() {
        return loseCondition;
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.PLAYER_LOST;
    }
}

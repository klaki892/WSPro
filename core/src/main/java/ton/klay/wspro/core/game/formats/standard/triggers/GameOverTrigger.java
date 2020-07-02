package ton.klay.wspro.core.game.formats.standard.triggers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.api.game.player.GamePlayer;

import java.util.List;

public class GameOverTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    private final List<GamePlayer> losingPlayers;

    public GameOverTrigger(List<GamePlayer> losingPlayers, TriggerCause cause, GameEntity caller) {
        super(cause, caller);
        this.losingPlayers = losingPlayers;
    }

    public List<GamePlayer> getLosingPlayers() {
        return losingPlayers;
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.GAME_OVER;
    }
}

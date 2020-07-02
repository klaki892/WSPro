package ton.klay.wspro.core.game.events;

import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.Game;

/**
 * @author Klayton Killough
 * Date: 8/13/2017
 * An Interrupt-Type Rule Action Which halts gameplay to perform the action
 */
public abstract class InterruptRuleAction implements GameEntity, RuleAction {

    protected final GamePlayer player;
    protected final Game game;

    public InterruptRuleAction(GamePlayer player){
        this.player = player;
        game = player.getGame();
    }

    @Override
    public GamePlayer getMaster() {
        return player;
    }
}

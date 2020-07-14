package ton.klay.wspro.core.game.events;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import to.klay.wspro.core.game.proto.ProtoInterruptRuleAction;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.Game;

/**
 * @author Klayton Killough
 * Date: 8/13/2017
 * An Interrupt-Type Rule Action Which halts gameplay to perform the action
 */
@ProtoClass(ProtoInterruptRuleAction.class)
public abstract class InterruptRuleAction implements GameEntity, RuleAction {

    @ProtoField
    protected final GamePlayer player;

    protected final transient Game game;

    public InterruptRuleAction(GamePlayer player){
        this.player = player;
        game = player.getGame();
    }

    @Override
    public GamePlayer getMaster() {
        return player;
    }
}

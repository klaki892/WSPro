package ton.klay.wspro.core.game.effects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.cardLogic.ability.Ability;

public abstract class OwnableBaseEffect implements OwnableEffect {

    private static final Logger log = LogManager.getLogger();
    private final Ability owningAbility;

    public OwnableBaseEffect(Ability owningAbility){
        this.owningAbility = owningAbility;
    }

    @Override
    public Ability getOwningAbility() {
        return owningAbility;
    }

    @Override
    public GamePlayer getMaster() {
        return getOwningAbility().getMaster();
    }
}

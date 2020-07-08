package ton.klay.wspro.core.game.cardLogic.ability;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.abilities.Ability;
import ton.klay.wspro.core.api.cards.abilities.components.effects.Effect;
import ton.klay.wspro.core.api.game.player.GamePlayer;

public abstract class BaseEffect implements Effect {

    private static final Logger log = LogManager.getLogger();
    private final Ability owningAbility;

    public BaseEffect(Ability owningAbility){
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

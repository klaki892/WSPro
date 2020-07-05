package ton.klay.wspro.core.game.cardLogic.ability;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.Cost;
import ton.klay.wspro.core.api.cards.abilities.AbilityType;
import ton.klay.wspro.core.api.cards.abilities.components.effects.Effect;
import ton.klay.wspro.core.api.game.player.GamePlayer;

/**
 * An AbilityCategory of the category Activation
 * @see <code>Weiss Schwarz Rule 8.1.1.1</code>
 */
public abstract  class ActivatedAbility extends BaseAbility {

    private static final Logger log = LogManager.getLogger();

    private static final AbilityType category = AbilityType.ABILITY_ACTIVATED;

    private Cost cost = null;

    public ActivatedAbility(Cost cost, Effect effect) {
        this.cost = cost;
    }

    public Cost getCost() {
        return cost;
    }

    @Override
    public AbilityType getAbilityType() {
        return null;
    }

    @Override
    public void performEffect() {

    }

    @Override
    public GamePlayer getMaster() {
        return null;
    }
}

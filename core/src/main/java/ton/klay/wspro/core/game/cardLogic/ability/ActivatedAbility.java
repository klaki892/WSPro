package ton.klay.wspro.core.game.cardLogic.ability;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.abilities.AbilityCategory;

/**
 * An AbilityCategory of the category Activation
 * @see <code>Weiss Schwarz Rule 8.1.1.1</code>
 */
public class ActivatedAbility extends BaseAbility {

    private static final Logger log = LogManager.getLogger();

    private static final AbilityCategory category = AbilityCategory.ABILITY_ACTIVATED;

    private Cost cost = null;

    public ActivatedAbility(Cost cost, ton.klay.wspro.core.api.cards.abilities.components.effects.Effect effect) {
        super(category, effect);
        this.cost = cost;
    }

    public Cost getCost() {
        return cost;
    }
}

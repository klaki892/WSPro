package ton.klay.wspro.core.game.cardLogic.ability;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.abilities.AbilityCategory;

/**
 * An AbilityCategory of the category Continious
 *
 * @see <code>Weiss Schwarz Rule 8.1.1.3</code>
 */
public class ContinuousAbility extends BaseAbility {

    private static final Logger log = LogManager.getLogger();

    private static final AbilityCategory category = AbilityCategory.ABILITY_CONTINUOUS;

    public ContinuousAbility(ton.klay.wspro.core.api.cards.abilities.components.effects.Effect effect) {
        super(category, effect);
    }

}

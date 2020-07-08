package ton.klay.wspro.core.game.cardLogic.ability;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.Cost;
import ton.klay.wspro.core.api.cards.abilities.AbilityType;

/**
 * An AbilityCategory of the category Automatic
 * @see <code>Weiss Schwarz Rule 8.1.1.2</code>
 */
public abstract class AutomaticAbility extends BaseAbility {

    private static final Logger log = LogManager.getLogger();

    @Override
    public AbilityType getAbilityType() {
        return AbilityType.ABILITY_AUTOMATIC;
    }

    public abstract Cost getCost();
}
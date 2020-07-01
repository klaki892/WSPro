package ton.klay.wspro.core.game.cardLogic.ability;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.Cost;
import ton.klay.wspro.core.api.cards.abilities.AbilityType;
import ton.klay.wspro.core.api.cards.abilities.TriggerableAbility;
import ton.klay.wspro.core.api.cards.abilities.components.AbilityConditions;

/**
 * An AbilityCategory of the category Automatic
 * @see <code>Weiss Schwarz Rule 8.1.1.2</code>
 */
public class AutomaticAbility extends BaseAbility implements TriggerableAbility {

    private static final Logger log = LogManager.getLogger();

    private static final AbilityType category = AbilityType.ABILITY_AUTOMATIC;

    /**
     * Automatic abilities may hav ea cost associated with them
     * @see <code>Weiss Schwarz Rule 8.1.1.2.2</code>
     */
    private Cost cost = null;

    /**
     * Automatic abiltiies have a condition associated with them for them to be triggered <br>
     * As noted in the rules, the condition always starts with "When you..." in the description
     *
     */
    private AbilityConditions condition = null;


    /**
     * Defines an automatic abiltiiy that has a cost associated with the condition for activation of the effect.
     * @param cost - the actions needed to take place so that the effect may begin resolution
     * @param condition - the condition needed to mark an automatic ability as active
     * @param effect - the effect that the ability preforms
     * @see <code>Weiss Schwarz Rule 8.1.1.2.2</code>
     */
    public AutomaticAbility(Cost cost, AbilityConditions condition, ton.klay.wspro.core.api.cards.abilities.components.effects.Effect effect) {
        super(category, effect);
        this.condition = condition;
        this.cost = cost;
    }

    /**
     * Defines an automatic abiltiy that does not have a cost associated with the condition for activation of the effect
     * @param condition - the condition needed to mark an automatic ability as active
     * @param effect - the effect that the ability preforms
     * @see <code>Weiss Schwarz Rule 8.1.1.2.1</code>
     */
    public AutomaticAbility(AbilityConditions condition, ton.klay.wspro.core.api.cards.abilities.components.effects.Effect effect){
        this(null, condition, effect); //FIXME instead of being a null cost, there may be a constant for NO COST
    }

    public Cost getCost() {
        return cost;
    }

    public AbilityConditions getCondition() {
        return condition;
    }
}

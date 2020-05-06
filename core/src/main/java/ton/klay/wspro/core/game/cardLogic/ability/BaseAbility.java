package ton.klay.wspro.core.game.cardLogic.ability;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.abilities.AbilityCategory;
import ton.klay.wspro.core.api.cards.abilities.AbilityKeyword;

/**
 * The Base class for defining an ability
 */
public class BaseAbility implements ton.klay.wspro.core.api.cards.abilities.Ability {

    private static final Logger log = LogManager.getLogger();

    private AbilityCategory abilityType = null;
    private AbilityKeyword abilityKeyword = AbilityKeyword.KEYWORD_NONE;
    private ton.klay.wspro.core.api.cards.abilities.components.effects.Effect effect = null;

    /**
     * THe base constructor for defining an ability with a keyword for identification.
     * @param category - the category of the ability as provided by an enum
     * @param effect  - the enclosed effect of the ability
     * @param keyword - a keyword designation for the ability for identification
     */
    protected BaseAbility(AbilityCategory category, ton.klay.wspro.core.api.cards.abilities.components.effects.Effect effect, AbilityKeyword keyword){
        abilityType = category;
        this.effect = effect;
        abilityKeyword = keyword;
    }


    /**
     * THe base constructor for defining an ability
     * @param category - the category of the ability as provided by an enum
     * @param effect  - the enclosed effect of the ability
     */
    protected BaseAbility(AbilityCategory category, ton.klay.wspro.core.api.cards.abilities.components.effects.Effect effect){
        this(category, effect, AbilityKeyword.KEYWORD_NONE);
    }

    @Override
    public AbilityCategory getAbilityType() {
        return abilityType;
    }

    @Override
    public ton.klay.wspro.core.api.cards.abilities.components.effects.Effect getEffect() {
        return effect;
    }
}

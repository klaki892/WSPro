package ton.klay.wspro.core.game.cardLogic.ability;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.abilities.Ability;
import ton.klay.wspro.core.api.cards.abilities.AbilityKeyword;
import ton.klay.wspro.core.api.cards.abilities.AbilityType;
import ton.klay.wspro.core.api.cards.abilities.components.effects.Effect;

import java.util.Collection;

/**
 * The Base class for defining an ability
 */
public abstract class BaseAbility implements Ability {

    private static final Logger log = LogManager.getLogger();
    private final AbilityKeyword abilityKeyword;

    private Collection<AbilityKeyword> keywords;
    private Effect effect = null;


    /**
     * THe base constructor for defining an ability with a keyword for identification.
     * @param category - the category of the ability as provided by an enum
     * @param effect  - the enclosed effect of the ability
     * @param keyword - a keyword designation for the ability for identification
     */
    protected BaseAbility(AbilityType category, Effect effect, AbilityKeyword keyword){
        this.effect = effect;
        abilityKeyword = keyword;
    }




    /**
     * THe base constructor for defining an ability
     * @param category - the category of the ability as provided by an enum
     * @param effect  - the enclosed effect of the ability
     */
    protected BaseAbility(AbilityType category, Effect effect){
        this(category, effect, AbilityKeyword.KEYWORD_NONE);
    }

    public abstract AbilityType getAbilityType();

    @Override
    public Effect getEffect() {
        return effect;
    }

    public AbilityKeyword getAbilityKeyword() {
        return abilityKeyword;
    }
}

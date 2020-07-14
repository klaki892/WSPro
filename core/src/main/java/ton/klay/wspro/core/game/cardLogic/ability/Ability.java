package ton.klay.wspro.core.game.cardLogic.ability;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.abilities.AbilityKeyword;
import ton.klay.wspro.core.api.cards.abilities.AbilityType;
import ton.klay.wspro.core.api.cards.abilities.components.effects.Effect;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.game.effects.OwnableBaseEffect;

import java.util.List;

/**
 * The Base class for defining an ability
 */
public abstract class Ability implements GameEntity {

    private static final Logger log = LogManager.getLogger();

    protected transient OwnableBaseEffect effect;


    public abstract AbilityType getAbilityType();

    public abstract List<AbilityKeyword> getKeywords();

    public abstract void performEffect();

    public abstract Effect getEffect();

}

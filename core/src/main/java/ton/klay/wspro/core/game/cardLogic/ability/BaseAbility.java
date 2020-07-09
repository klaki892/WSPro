package ton.klay.wspro.core.game.cardLogic.ability;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.abilities.Ability;
import ton.klay.wspro.core.api.cards.abilities.AbilityKeyword;
import ton.klay.wspro.core.game.effects.OwnableBaseEffect;

import java.util.Collection;

/**
 * The Base class for defining an ability
 */
public abstract class BaseAbility implements Ability {

    private static final Logger log = LogManager.getLogger();

    protected Collection<AbilityKeyword> keywords;
    protected OwnableBaseEffect effect;


}

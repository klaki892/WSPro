package ton.klay.wspro.core.game.cardLogic.ability;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.abilities.Ability;
import ton.klay.wspro.core.api.cards.abilities.AbilityType;
import ton.klay.wspro.core.api.game.player.GamePlayer;

/**
 * An AbilityCategory of the category Continious
 *
 * @see <code>Weiss Schwarz Rule 8.1.1.3</code>
 */
public abstract class ContinuousAbility extends BaseAbility implements Ability {

    private static final Logger log = LogManager.getLogger();

    private static final AbilityType category = AbilityType.ABILITY_CONTINUOUS;

    public ContinuousAbility(ton.klay.wspro.core.api.cards.abilities.components.effects.Effect effect) {
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

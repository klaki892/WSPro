package ton.klay.wspro.core.game.cardLogic.ability;

import com.google.common.eventbus.EventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.abilities.AbilityType;
import ton.klay.wspro.core.api.cards.abilities.TriggerableAbility;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;

/**
 * An AbilityCategory of the category Continious
 *
 * @see <code>Weiss Schwarz Rule 8.1.1.3</code>
 */
public class ContinuousAbility extends BaseAbility implements TriggerableAbility {

    private static final Logger log = LogManager.getLogger();

    private static final AbilityType category = AbilityType.ABILITY_CONTINUOUS;

    public ContinuousAbility(ton.klay.wspro.core.api.cards.abilities.components.effects.Effect effect) {
        super(category, effect);
    }

    @Override
    public void triggerReceived(BaseTrigger trigger) {

    }

    @Override
    public void register(EventBus eventBus) {

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

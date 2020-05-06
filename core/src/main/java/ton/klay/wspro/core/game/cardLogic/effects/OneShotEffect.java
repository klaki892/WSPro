package ton.klay.wspro.core.game.cardLogic.effects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.abilities.components.effects.EffectCategory;

/**
 * Defines a event of the type One-Shot
 * @see <code>Weiss Schwarz Rule 8.2.1.1</code>
 */
public class OneShotEffect extends Effect {

    private static final Logger log = LogManager.getLogger();

    private static final EffectCategory category = EffectCategory.EFFECT_ONE_SHOT;
    /**
     * Defines a event of the type One-Shot <br>
     *     “One-shot abilities” refer to abilities that resolve by executing that action, and
     ending the effect thereafter. For example,
     abilities that are triggered by abilities such
     as “draw a card” or “put this character into
     the waiting room” are one-shot abilities.
     * @see <code>Weiss Schwarz Rule 8.2.1.1</code>
     */
    public OneShotEffect() {
        super(category);
    }
}

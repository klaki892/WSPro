package ton.klay.wspro.core.game.cardLogic.effects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.abilities.components.effects.EffectCategory;
import ton.klay.wspro.core.api.cards.abilities.components.effects.components.EffectDuration;

/**
 * Defines a event of the type Continuous
 * @see <code>Weiss Schwarz Rule 8.2.1.2</code>
 */
public class ContinuousEffect extends Effect {

    private static final Logger log = LogManager.getLogger();

    private static final EffectCategory category = EffectCategory.EFFECT_CONTINUOUS;

    private EffectDuration duration = null;

    /**
     * Defines a event of the type Continuous <br>
     “Continuous abilities” refer to abilities that
     remain in effect for a specified duration
     (including “during this game”, for abilities
     with no specified duration). For example,
     abilities that are triggered by abilities such
     as “all of your characters in front of this
     card get +500 power” or “this card gets +1
     soul until end of turn” are continuous
     abilities
     * @param duration - Defines the duration that a continious effect will take hold
     * @see <code>Weiss Schwarz Rule 8.2.1.2</code>
     */
    public ContinuousEffect(EffectDuration duration) {
        super(category);
        this.duration = duration;
    }
}

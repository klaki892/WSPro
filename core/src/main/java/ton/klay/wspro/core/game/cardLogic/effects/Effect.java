package ton.klay.wspro.core.game.cardLogic.effects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.abilities.components.effects.EffectCategory;

/**
 * A Wrapper for a specific type of effect as defined in the game
 * @see <code>Weiss Schwarz Rule 8.2</code>
 */
public class Effect implements ton.klay.wspro.core.api.cards.abilities.components.effects.Effect {

    private static final Logger log = LogManager.getLogger();

    private EffectCategory category = null;

    /**
     * Defines a particular effect with a category based on known categories of the rules
     * @param category - The Category of the effect as defined in the rules
     * @see <code>Weiss Schwarz Rule 8.2.1</code>
     */
    protected Effect(EffectCategory category){
        this.category = category;
    }

    @Override
    public EffectCategory getCategory() {
        return category;
    }
}

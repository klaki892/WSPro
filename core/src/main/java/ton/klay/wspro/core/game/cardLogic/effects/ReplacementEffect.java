package ton.klay.wspro.core.game.cardLogic.effects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import ton.klay.wspro.core.api.cards.abilities.components.effects.EffectCategory;

/**
 * Defines a event of the type Replacement <br>
 * There are few actual examples of this type of effect, but you will normally see the definition play out in "anti-" cards (e.g. Anti heal AKA Heal Tax.)
 * @see <code>Weiss Schwarz Rule 8.2.1.3</code>
 */
public class ReplacementEffect extends Effect {

    private static final Logger log = LogManager.getLogger();

    private static final EffectCategory category = EffectCategory.EFFECT_REPLACEMENT;
    /**
     * Defines a event of the type Replacement <br>
     “Replacement abilities” refer to abilities
     that replace the execution of a situation
     during the game with the execution of
     another situation instead.
     * @see <code>Weiss Schwarz Rule 8.2.1.3</code>
     */
    public ReplacementEffect() {
        super(category);
        log.error("Replacement abilities are not yet implemented.");
        throw new NotImplementedException();
    }

    //FIXME: This type of effect is very hard becuase it requires reflection on the state of the game. Click for more info
    /*
    because of this ruling:
    8.2.1.3.1. If an ability is written as “if (player)
    would do A, (player may) instead do B”,
    the effect that is triggered by that ability
    is a replacement ability.

    8.2.1.3.2. If an ability is written as “When
    (player) does A, (player) may [Choice].
    If (player) does, do B”, the effect that is
    triggered by that ability is an optional
    replacement effect.

    THis effect requires reflection into the currently primed abilities or actions.
    At the time of Writing, we have no current codebase
    for primed abilities or actions or utilty to modify and check them
    Thus making this effect definition impossible at the time of writing.
     */
}

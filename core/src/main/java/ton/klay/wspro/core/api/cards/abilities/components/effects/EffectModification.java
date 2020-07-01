package ton.klay.wspro.core.api.cards.abilities.components.effects;

import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

/**
 * Implementations modify a particular {@link IPlayingCard} property when called upon. <br/>
 * This is to be used for implementing continuous effects which cause stat modifications to specific cards.
 * @param <T>
 */
public interface EffectModification<T> {
    /**
     * Method which checks if this modification should still apply to a particular property when the property is queried.
     * @param effectOwner - the card which hosts this modification (probably is the target for the conditional)
     * @param context - the context in which the game is executing
     * @return a boolean on whether to still apply this stat (e.g. call the {@link #applyModification(Object)}
     */
    boolean isStillValid(PlayingCard effectOwner, GameContext context);

    /**
     * Applies the modification to the given variable that is passed to it
     * @param stat the variable that should be modified
     * @return the value after the variable <code>stat</code> has been modified
     */
    T applyModification(T stat);

}

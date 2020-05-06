package ton.klay.wspro.core.api.cards.abilities.components.effects;

import ton.klay.wspro.core.api.game.setup.GameContext;

/**
 * Implementations describe a continuous effect. <br/>
 * In the comprehensive rules, these effects are described as <b>always active</b> and thus should be checked to see if they
 * can be applied at almost every given time during the game
 */
public interface ContinuousEffect {

    /**
     * Implementations of this method should do the following: (in the recommended order)
     * <ol>
     *     <li>Check a conditional to apply the effect (e.g. owning card of effect is on field) </li>
     *     <li>Create an {@link EffectModification} and apply it to a particular card </li>
     *     <li>(if this was a temporary effect) destroy self if the conditional to apply is false.</li>
     * </ol>
     * @param ownerGUID the GUID (Game-Unique-ID) of the card which created this effect
     * @param context the context in which the current game is running
     */
    void update(String ownerGUID, GameContext context);
}

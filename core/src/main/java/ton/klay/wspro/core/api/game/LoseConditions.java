package ton.klay.wspro.core.api.game;

public enum LoseConditions {

    /**
     * The Lose Condition for a player hitting level 4 in their Level Zone.<br/><br/>
     * @see  <code>Weiss Schwarz Rule 1.2.2.1</code>
     */
    LEVEL4,
    /**
     * The Lose Condition for a player having no more cards in their deck or their waiting room<br/><br/>
     * @see  <code>Weiss Schwarz Rule 1.2.2.2</code>
     */
    EMPTY_DECK_AND_WAITING,

    /**
     * The lose condition for both player's losing at the same time. <br><br>
     *     Side note: Yes, it is possible. Specific case example is the effect from FT/EN-S02-037 U.
     * @see <code>Weiss Schwarz Rule 1.2.3</code>
     */
    DRAW,

    /**
     * The Lose Condition for a player conceding. (not technically a lose condition, but deal with it.)<br/><br/>
     * @see <code>Weiss Schwarz Rule 1.2.4</code>
     */
    CONCEDE,

    /**
     * The Lose Condition for a player losing by the effect of a card.<br/><br/>
     * @see <code>Weiss Schwarz Rule 1.2.5 </code>
     *
     */
    CARD_EFFECT,

    /**
     * A special lose condition to indicate the game was ended prematurely, no one should be a loser in this situation.
     */
    GAME_ERROR

}

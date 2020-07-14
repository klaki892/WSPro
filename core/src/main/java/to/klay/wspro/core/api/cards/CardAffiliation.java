package to.klay.wspro.core.api.cards;

/**
 * The Affiliation of a particular card in the game
 */
public enum CardAffiliation {

    /**
     * A card affiliated to the Weiss side of the game (White wings on the frame of the card)<br><br/>
     *
     * @see  <code>Weiss Schwarz Rule 2.18.1.1 </code>
     */
    AFFILIATION_WEISS,

    /**
     * A card affiliated to the Weiss side of the game (Black wings on the frame of the card)<br><br/>
     *
     * @see <code>Weiss Schwarz Rule 2.18.1.2 </code>
     */
    AFFILIATION_SCHWARZ,

    /**
     * A card affiliated to the both sides (Weiss and Schwarz) side of the game (white and black wings on the frame of the card)<br><br/>
     *
     * @see  <code>Weiss Schwarz Rule 2.18.1</code>
     */
    AFFILIATION_BOTH

}

package to.klay.wspro.core.api.cards;

/**
 * The Types of Card CardTrigger available in the game.
 * @see <code>Weiss Schwarz rule 4.12.2</code>
 */
public enum CardTrigger {

    /**
     * No CardTrigger
     * @see <code>Weiss Schwarz rule 4.12.2.1</code>
     */
    NONE,

    /**
     * Soul Event
     * @see <code>Weiss Schwarz Rule 4.12.2.2</code>
     */
    SOUL,

    /**
     * Return Icon. (commonly known as "bounce" or "wind")
     * @see <code> Weiss Schwarz rule 4.12.2.3</code>
     */
    RETURN,

    /**
     * Pool Icon. Player may put top card of deck into stock.
     * @see <code> Weiss Schwarz rule 4.12.2.4</code>
     */
    POOL,

    /**
     * Come-Back Icon. (Commonly known as "salvage")
     * @see <code>Weiss Schwarz rule 4.12.2.5</code>
     */
    COMEBACK,

    /**
     * Draw Icon. (Commonly known as "book")
     * @see <code>Weiss Schwarz rule 4.12.2.6</code>
     */
    DRAW,

    /**
     * Shot Icon.
     * @see <code>Weiss Schwarz rule 4.12.2.7</code>
     */
    SHOT,

    /**
     * Treasure Icon. (commonly known as "gold bar")
     * @see  <code>Weiss Schwarz rule 4.12.2.8</code>
     */
    TREASURE,

    /**
     * Gate Icon. (commonly known as "pants")
     * @see <code> Weiss Schwarz rule 4.12.2.9</code>
     */
    GATE,

    /**
     * Standby. (not commonly known as "pumpkin")
     * @see <code>Weiss Schwarz rule 4.12.2.10</code>
     */
    STANDBY,

    CHOICE
}

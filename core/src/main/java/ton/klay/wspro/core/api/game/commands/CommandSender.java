package ton.klay.wspro.core.api.game.commands;

/**
 * An Implementation of an entity that issues commands. <br/>
 * Knowing the issuer of a command can help with logging, access control, and game flow.
 *  (e.g. dont let player 1 move his cards on player 2's main phase)
 */
public interface CommandSender {

    /**
     * Return the string form of the name of a sender.<br/>
     * Examples for this could include the ID of a player, or the UUID of a particular card
     * @return a name for this CommandSender
     */
    String getName();

    /**
     * Get the general category type of this Sender
     * @return a value representing the category of the command sender
     */
    CommandSenderType getSenderType();
}

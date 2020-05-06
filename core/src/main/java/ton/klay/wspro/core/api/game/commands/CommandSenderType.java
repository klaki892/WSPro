package ton.klay.wspro.core.api.game.commands;

/**
 * Types used to enumerate What general category a {@link CommandSender} represents.
 * @see CommandSender
 */
public enum CommandSenderType {

    PLAYER,
    CARD,
    /**
     * Identifier used for when the game issue a command,
     * recommended for system level events such as starting the game phase.
     */
    GAME,
    OBSERVER


}

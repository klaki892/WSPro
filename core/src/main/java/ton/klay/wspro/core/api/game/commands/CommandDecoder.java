package ton.klay.wspro.core.api.game.commands;

import ton.klay.wspro.core.api.game.communication.Communicable;

public interface CommandDecoder {

    /**
     * Decodes a command that it recieves. If decoding isn't possible a null value should be returned, or an exception should be thrown.
     * @param message the message which likely contains a command
     * @param sender the sender of the message, all executable commands need to know the sender
     * @return a executable command
     */
    Command decode(Communicable message, CommandSender sender);
}

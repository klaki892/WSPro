package ton.klay.wspro.core.api.game.communication;

import ton.klay.wspro.core.api.game.commands.CommandSender;
import ton.klay.wspro.core.api.game.commands.CommandSenderType;
import ton.klay.wspro.core.api.game.events.Event;
import ton.klay.wspro.core.api.game.setup.GameContext;

/**
 * Implementations have the job of dealing with communication between {@link Communicator}'s and a Running Weiss game that dispatches {@link Communicable}s
 */
public interface GameCommunicationManager {

    void sendMessage(Communicable message, String communicatorID);

    void messageAll(Communicable message);

    void decode(Communicable message, CommandSender sender);

    void encode (Event event);

    void setContext(GameContext context);

    void addCommunicator(Communicator communicator, CommandSenderType senderType);

    void removeCommunicator(Communicator communicator);

}

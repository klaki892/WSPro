package ton.klay.wspro.core.api.game.communication;

/**
 * Interface used for communicating with the player for getting and sending commands and question to the player <br/>
 * by necessity implementations of Communicators <b>Must be able to handle Asynchronous passing of messages</b> <br/>
 * This requirement is necessary for games to be dispatch messages to multiple entities without freezing the game.
 */
public interface Communicator {

    /**
     * Sends a message to a particular entity. <br/>
     * The implementation should be non-blocking and return immediately irregardless of delivery of content.
     *
     * @param message a container of content to be transmitted
     */
    void sendMessage(Communicable message);

    /**
     * Adds a receiver to obtain the messages that this communicator passes.
     *
     * @param communicator the target communicator that will receive  messages sent by this communicator.
     */
    void addMessageReceiver(Communicator communicator);

    void removeMessageReceiver(Communicator communicator);

    /**
     * Obtains a traceable ID with the communicator
     *
     * @return an ID associated to this communicator
     */
    String getID();

    void receiveMessage(Communicable message);
}

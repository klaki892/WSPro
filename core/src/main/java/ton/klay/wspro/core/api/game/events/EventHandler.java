package ton.klay.wspro.core.api.game.events;

/**
 * An Implementation capable of dealing with Event Objects and also broadcasting them to {@link EventListener}
 *
 */
public interface EventHandler {
    /**
     * Gives an Event for the for EventHandler to Handle, with the most likely implementation being that the EventHandler
     *  will notify any {@link EventListener } which has added itself
     * @param event an event that this handler should do something with. (i.e let the listeners know about this event)
     */
    void issueEvent(Event event);

    /**
     * Allows for {@link EventListener}'s to "subscribe" to this EventHandler's messages.
     * @param eventListener an event Listener to add to this handler
     */
    void addEventListener(EventListener eventListener);

    /**
     * Stops a registered event Listener from receiving anymore of the Events that are issued.
     * @param eventListener an event listener to remove from this handler
     */
    void removeEventListener(EventListener eventListener);
}

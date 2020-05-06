package ton.klay.wspro.core.api.game.events;

/** A manager of {@link ton.klay.wspro.core.api.game.events.EventHandler}s. <br/>
 * Deciding to use such an object converts the design of an event system from an Observer to a Publisher/Subscriber design pattern.
 * This is a major design shift for {@link EventListener}, as the burden of handling unrelated events is passed to
 * manager instead of the listener disregarding them.
 */
public interface EventManager extends EventHandler {

    /**
     * Additional method to allow a eventLister to add itself but request to only be notified of specific keywords
     * @param eventListener an event Listener to add to this handler
     * @param eventKeyword 0 or more keywords the listener would like to be notified about
     * @see Event
     */
    void addEventListener(EventListener eventListener, String... eventKeyword);
}

package ton.klay.wspro.core.api.game.events;

public interface EventListener {
    /**
     * Method which defines what to do when a event is recieved
     * @param event
     */
    void update(Event event);
}

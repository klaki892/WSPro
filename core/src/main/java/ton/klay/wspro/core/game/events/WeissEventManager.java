package ton.klay.wspro.core.game.events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.events.Event;
import ton.klay.wspro.core.api.game.events.EventManager;
import ton.klay.wspro.core.api.game.events.EventListener;
import ton.klay.wspro.core.game.commands.highlevel.PlayerResponseCommand;
import ton.klay.wspro.core.game.events.gameissued.GameEndEvent;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A Manager that implements the publisher subscriber design pattern for handling multiple event handlers.
 */
public class WeissEventManager implements EventManager {

    private static final Logger log = LogManager.getLogger();
    private HashMap<String, Set<EventListener>> subscriptions;
    private ExecutorService queueWorkerExecutor;

    /**
     * Special subscription keyword which distributes any event received to the list.
     */
    private static final String ALL_EVENTS = "ALL";

    //it was a 2 hours decision starting here whether to make the Event manager & Commander Executor Asynchronous,
    //We chose to let it be synchronous. Only time will tell if this was a good decision.

    public WeissEventManager(){
        subscriptions = new HashMap<>();
        subscriptions.put(ALL_EVENTS, new HashSet<>());
        queueWorkerExecutor = Executors.newSingleThreadExecutor();

        //create a new GameEndEvent Listener that will tell us to shut down our executor thread.
         addEventListener(event -> {
             queueWorkerExecutor.shutdown();
             log.info("Shutting down Event Manager...");
         }, GameEndEvent.EVENT_NAME);
    }


    @Override
    public synchronized void  issueEvent(Event event) {

        //Executors have an unbounded internal queue.
        queueWorkerExecutor.execute(() -> dispatchEvent(event));

    }

    /**
     * A method used to allow asynchronous receiving and dispatching of events
     */
    private void dispatchEvent(Event event){
        try {
            log.debug("Dispatching Event: " + event.toCommunicableString());
            Set<EventListener> receivingListeners;

            //player responses are for the listeners only, no one else
            if (!(event instanceof PlayerResponseCommand)) {
                receivingListeners = new HashSet<>(subscriptions.get(ALL_EVENTS));
            } else {
                receivingListeners = new HashSet<>();
            }

            if (subscriptions.containsKey(event.getName()))
                receivingListeners.addAll(subscriptions.get(event.getName()));

            for (EventListener eventListener : receivingListeners) {
                eventListener.update(event);
            }
        } catch (RuntimeException rex){
            log.fatal("error occurred while dispatching events.", rex);

        }
    }

    /**
     * Adds an event Listener to the manager. Since a specific event is not specified, this listener will receive all
     * events the manager receives.
     * @param eventListener an event Listener to add to this Manager, will receive all events issued by the Manager.
     */
    @Override
    public void addEventListener(EventListener eventListener) {
        subscriptions.get(ALL_EVENTS).add(eventListener);
        log.debug("added new listener for \"" + ALL_EVENTS + "\" Keyword");
    }

    @Override
    public void removeEventListener(EventListener eventListener) {
        log.debug("removing A Listener");
        for (Set<EventListener> listenerList : subscriptions.values()) {
            listenerList.remove(eventListener);
        }
    }

    @Override
    public void addEventListener(EventListener eventListener, String... eventKeyword) {
        for (String keyword : eventKeyword) {
            if (subscriptions.containsKey(keyword)){
                subscriptions.get(keyword).add(eventListener);
                log.debug("added new Subscription & listener for \"" + keyword + "\" Keyword");

            }
            else {
                Set<EventListener> newSet = new HashSet<>();
                newSet.add(eventListener);
                subscriptions.put(keyword, newSet);
                log.debug("added new listener for \"" + keyword + "\" Keyword");

            }
        }
    }
}

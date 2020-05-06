package ton.klay.wspro.core.api.game.communication.rules;

import ton.klay.wspro.core.api.game.communication.Communicator;
import ton.klay.wspro.core.api.game.events.Event;

import java.util.Collection;

/**
 * Manages how to deal with communicating an event and who to dispatch it to.
 */
public interface CommunicationRuleBook {

    /**
     * Looks up a dispatch rule, returning a list of ID's that the event should go to.
     * @param event the event that needs to be sent to specific (or all) communicators
     * @param communicators a list of communicator to check the rule aganist
     * @return which ID's to send the event to.
     */
    Collection<String> lookupDispatchRule(Event event, Collection<Communicator> communicators);
}

package ton.klay.wspro.core.game.events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.events.Event;

import java.util.Arrays;
import java.util.StringJoiner;

public abstract class BaseWeissEvent implements Event {

    private static final Logger log = LogManager.getLogger();

    @Override
    public String toCommunicableString() {

        return "EVENT " + getName() + " " + String.join(" ", getArgs());
    }
}

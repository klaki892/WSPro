package ton.klay.wspro.core.game.events.gameissued;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.commands.Command;
import ton.klay.wspro.core.api.game.events.Event;
import ton.klay.wspro.core.game.events.BaseWeissEvent;

public class InvalidCommandEvent extends BaseWeissEvent {

    public static final String EVENT_NAME = "INVALIDCOMMAND";

    private static final Logger log = LogManager.getLogger();
    private final Command command;

    public InvalidCommandEvent(Command command) {

        this.command = command;
    }

    @Override
    public String getName() {
        return EVENT_NAME;
    }

    @Override
    public String[] getArgs() {
        return new String[]{command.toCommunicableString()};
    }

    @Override
    public boolean hasArgs() {
        return true;
    }
}

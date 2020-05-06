package ton.klay.wspro.core.game.events.highlevel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.game.commands.highlevel.PlayerResponseCommand;
import ton.klay.wspro.core.game.commands.highlevel.PlaytimingCommand;
import ton.klay.wspro.core.game.events.BaseWeissEvent;

public class PlaytimingEvent extends BaseWeissEvent {

    public static final String EVENT_NAME = PlaytimingCommand.CMD_NAME;
    private static final Logger log = LogManager.getLogger();
    private final String[] requestArgs;

    // REQUEST ID [ADDITIONAL ARGS]

    public PlaytimingEvent(String[] args) {

        this.requestArgs = args;
    }

    @Override
    public String getName() {
        return EVENT_NAME;
    }

    @Override
    public String[] getArgs() {
        return requestArgs;
    }

    @Override
    public boolean hasArgs() {
        return true;
    }
}

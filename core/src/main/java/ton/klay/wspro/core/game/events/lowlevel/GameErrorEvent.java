package ton.klay.wspro.core.game.events.lowlevel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.game.commands.lowlevel.GameErrorCommand;
import ton.klay.wspro.core.game.events.BaseWeissEvent;

public class GameErrorEvent extends BaseWeissEvent {

    private static final Logger log = LogManager.getLogger();
    public static final String EVENT_NAME = GameErrorCommand.CMD_NAME;


    @Override
    public String getName() {
        return EVENT_NAME;
    }

    @Override
    public String[] getArgs() {
        return new String[0];
    }

    @Override
    public boolean hasArgs() {
        return false;
    }
}

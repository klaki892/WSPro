package ton.klay.wspro.core.game.events.midlevel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.events.Event;
import ton.klay.wspro.core.game.commands.midlevel.ShuffleCommand;
import ton.klay.wspro.core.game.events.BaseWeissEvent;

public class ShuffleEvent extends BaseWeissEvent {

    public static final String EVENT_NAME = ShuffleCommand.CMD_NAME;
    private static final Logger log = LogManager.getLogger();
    private final String playerID;
    private final String zoneName;

    public ShuffleEvent(String playerID, String zoneName) {
        this.playerID = playerID;
        this.zoneName = zoneName;
    }

    @Override
    public String getName() {
        return EVENT_NAME;
    }

    @Override
    public String[] getArgs() {
        return new String[]{playerID, zoneName};
    }

    @Override
    public boolean hasArgs() {
        return true;
    }
}

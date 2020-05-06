package ton.klay.wspro.core.game.events.gameissued;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.events.Event;
import ton.klay.wspro.core.game.commands.lowlevel.GameStartCommand;
import ton.klay.wspro.core.game.events.BaseWeissEvent;

import java.util.UUID;

public class GameStartEvent extends BaseWeissEvent {

    public static final String EVENT_NAME = GameStartCommand.CMD_NAME;
    private static final Logger log = LogManager.getLogger();
    private final String gameID;


    public GameStartEvent(String gameID) {
        this.gameID = gameID;
    }

    @Override
    public String getName() {
        return EVENT_NAME;
    }

    @Override
    public String[] getArgs() {
        return new String[]{gameID};
    }

    @Override
    public boolean hasArgs() {
        return true;
    }
}

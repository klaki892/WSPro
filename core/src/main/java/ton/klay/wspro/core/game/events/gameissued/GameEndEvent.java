package ton.klay.wspro.core.game.events.gameissued;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.events.Event;
import ton.klay.wspro.core.game.commands.lowlevel.GameEndCommand;
import ton.klay.wspro.core.game.events.BaseWeissEvent;

public class GameEndEvent extends BaseWeissEvent {

    private static final Logger log = LogManager.getLogger();


    public static final String EVENT_NAME = GameEndCommand.CMD_NAME;
    private final String gameID;

    //todo gameEndEvent should report the loss and the reason
    public GameEndEvent(String gameID) {
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

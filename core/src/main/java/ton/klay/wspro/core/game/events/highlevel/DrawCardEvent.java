package ton.klay.wspro.core.game.events.highlevel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.game.commands.highlevel.DrawCardCommand;
import ton.klay.wspro.core.game.commands.midlevel.MoveCardCommand;
import ton.klay.wspro.core.game.events.BaseWeissEvent;

public class DrawCardEvent extends BaseWeissEvent {

    public static final String EVENT_NAME = DrawCardCommand.CMD_NAME;
    private static final Logger log = LogManager.getLogger();
    private final String srcPlayerID;

    //MOVE [SRC PLAYER ID] [SRC ZONE NAME] [DST PLAYER ID] [DST ZONE NAME] [(optional) CARD ID (not broadcasted)

    public DrawCardEvent(String srcPlayerID) {

        this.srcPlayerID = srcPlayerID;
    }

    @Override
    public String getName() {
        return EVENT_NAME;
    }

    @Override
    public String[] getArgs() {
        return new String[]{srcPlayerID};
    }

    @Override
    public boolean hasArgs() {
        return true;
    }
}

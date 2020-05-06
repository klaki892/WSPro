package ton.klay.wspro.core.game.events.midlevel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.game.commands.midlevel.MoveCardCommand;
import ton.klay.wspro.core.game.commands.midlevel.ShuffleCommand;
import ton.klay.wspro.core.game.events.BaseWeissEvent;

public class MoveCardEvent extends BaseWeissEvent {

    public static final String EVENT_NAME = MoveCardCommand.CMD_NAME;
    private static final Logger log = LogManager.getLogger();
    private final String srcPlayerID;
    private final String srcZoneName;
    private final String dstPlayerID;
    private final String dstZoneName;

    //MOVE [SRC PLAYER ID] [SRC ZONE NAME] [DST PLAYER ID] [DST ZONE NAME] [(optional) CARD ID (not broadcasted)

    public MoveCardEvent(String srcPlayerID, String srcZoneName, String dstPlayerID, String dstZoneName) {

        this.srcPlayerID = srcPlayerID;
        this.srcZoneName = srcZoneName;
        this.dstPlayerID = dstPlayerID;
        this.dstZoneName = dstZoneName;
    }

    @Override
    public String getName() {
        return EVENT_NAME;
    }

    @Override
    public String[] getArgs() {
        return new String[]{srcPlayerID, srcZoneName, dstPlayerID, dstZoneName};
    }

    @Override
    public boolean hasArgs() {
        return true;
    }
}

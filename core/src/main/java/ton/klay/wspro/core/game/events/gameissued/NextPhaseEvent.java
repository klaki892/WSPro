package ton.klay.wspro.core.game.events.gameissued;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.events.Event;
import ton.klay.wspro.core.game.commands.lowlevel.NextPhaseCommand;
import ton.klay.wspro.core.game.events.BaseWeissEvent;

public class NextPhaseEvent extends BaseWeissEvent {

    public static final String EVENT_NAME = NextPhaseCommand.CMD_NAME;

    private static final Logger log = LogManager.getLogger();
    private final String phaseName;
    private final String playerId;

    public NextPhaseEvent(String phaseName, String playerId) {

        this.phaseName = phaseName;
        this.playerId = playerId;
    }

    @Override
    public String getName() {
        return EVENT_NAME;
    }

    @Override
    public String[] getArgs() {
        return new String[]{phaseName, playerId};
    }

    @Override
    public boolean hasArgs() {
        return true;
    }
}

package ton.klay.wspro.core.api.game.events;

import ton.klay.wspro.core.api.game.communication.Communicable;

public interface Event extends Communicable {
    String getName();

    String[] getArgs();

    boolean hasArgs();
}

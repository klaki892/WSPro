package ton.klay.wspro.core.game.formats.standard.triggers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BaseTrigger {

    private static final Logger log = LogManager.getLogger();

    public abstract TriggerName getTriggerName();

}

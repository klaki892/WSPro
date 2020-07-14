package ton.klay.wspro.core.game.formats.standard.triggers;

import com.google.common.base.MoreObjects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.GameEntity;

public abstract class BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    private final TriggerCause cause;
    private final transient GameEntity caller;

    public BaseTrigger(TriggerCause cause, GameEntity caller) {
        this.cause = cause;
        this.caller = caller;
    }

    public abstract TriggerName getTriggerName();

    public TriggerCause getCause() {
        return cause;
    }

    public GameEntity getCaller() {
        return caller;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("Name", getTriggerName())
                .add("cause", cause)
                .add("caller", caller)
                .toString();
    }
}

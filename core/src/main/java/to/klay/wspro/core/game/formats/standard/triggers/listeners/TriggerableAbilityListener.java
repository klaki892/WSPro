package to.klay.wspro.core.game.formats.standard.triggers.listeners;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import to.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;

public interface TriggerableAbilityListener {

    @Subscribe
    void triggerReceived(BaseTrigger trigger);

    void register(EventBus eventBus);

    void deregister();

}

package ton.klay.wspro.core.api.cards.abilities;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import ton.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;

/**
 * An ability that needs to subscribe to events to know when to act.
 */
public interface TriggerableAbility extends Ability{

    @Subscribe
    void triggerReceived(BaseTrigger trigger);

    void register(EventBus eventBus);
}

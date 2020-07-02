package ton.klay.wspro.core.game.formats.standard.triggers;

import com.google.common.eventbus.Subscribe;
import ton.klay.wspro.core.game.Game;

public abstract class TriggerObserver<T extends BaseTrigger> {

    protected final Game game;

    public TriggerObserver(Game game){
        this.game = game;
        game.getTriggerManager().register(this);
    }

    @Subscribe
    public abstract void triggerReceived(T trigger);

}

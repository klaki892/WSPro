package ton.klay.wspro.core.game.formats.standard.triggers.listeners;

import ton.klay.wspro.core.game.Game;

public abstract class BaseObserver {

    protected final Game game;

    public BaseObserver(Game game){
        this.game = game;
        this.game.getTriggerManager().register(this);
    }

}

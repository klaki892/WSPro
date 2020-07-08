package ton.klay.wspro.core.game.scripting.lua;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.luaj.vm2.LuaFunction;
import ton.klay.wspro.core.api.cards.abilities.Ability;
import ton.klay.wspro.core.api.cards.abilities.components.effects.Effect;
import ton.klay.wspro.core.api.game.player.GamePlayer;

public class LuaEffect implements Effect {

    private static final Logger log = LogManager.getLogger();
    private final LuaFunction executionFunction;
    private Ability owner;

    public LuaEffect(LuaFunction executionFunction, Ability owner){
        this.executionFunction = executionFunction;
        this.owner = owner;
    }
    @Override
    public void execute(Object... vars) {
        executionFunction.call();
    }

    @Override
    public Ability getOwningAbility() {
        return owner;
    }

    @Override
    public GamePlayer getMaster() {
        return owner.getMaster();
    }

    public void setOwner(Ability owner) {
        this.owner = owner;
    }

    public static class Builder {
        private LuaFunction executionFunction;
        private Ability owner;

        public Builder setExecutionFunction(LuaFunction executionFunction) {
            this.executionFunction = executionFunction;
            return this;
        }

        public Builder setOwner(Ability owner) {
            this.owner = owner;
            return this;
        }

        public LuaEffect createLuaEffect() {
            return new LuaEffect(executionFunction, owner);
        }
    }
}

package ton.klay.wspro.core.game.scripting.lua;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.luaj.vm2.LuaFunction;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.cardLogic.ability.Ability;
import ton.klay.wspro.core.game.effects.OwnableEffect;

public class LuaEffect implements OwnableEffect {

    private static final Logger log = LogManager.getLogger();
    private final LuaFunction executionFunction;
    private Ability owner;
    private final Object var1;
    private final Object var2;
    private final Object var3;
    private final Object var4;
    private final Object var5;

    public LuaEffect(LuaFunction executionFunction,
                     Ability owner,
                     Object var1,
                     Object var2,
                     Object var3,
                     Object var4,
                     Object var5){
        this.executionFunction = executionFunction;
        this.owner = owner;
        this.var1 = var1;
        this.var2 = var2;
        this.var3 = var3;
        this.var4 = var4;
        this.var5 = var5;
    }
    @Override
    public void execute(Object... vars) {
        executionFunction.call();
    }

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

    public Object getVar1() {
        return var1;
    }

    public Object getVar2() {
        return var2;
    }

    public Object getVar3() {
        return var3;
    }

    public Object getVar4() {
        return var4;
    }

    public Object getVar5() {
        return var5;
    }

    public static class Builder {
        private LuaFunction executionFunction;
        private Ability owner;
        private Object var1;
        private Object var2;
        private Object var3;
        private Object var4;
        private Object var5;

        public Builder setExecutionFunction(LuaFunction executionFunction) {
            this.executionFunction = executionFunction;
            return this;
        }

        public Builder setOwner(Ability owner) {
            this.owner = owner;
            return this;
        }

        public Builder setVar1(Object var1) {
            this.var1 = var1;
            return this;
        }

        public Builder setVar2(Object var2) {
            this.var2 = var2;
            return this;
        }

        public Builder setVar3(Object var3) {
            this.var3 = var3;
            return this;
        }

        public Builder setVar4(Object var4) {
            this.var4 = var4;
            return this;
        }

        public Builder setVar5(Object var5) {
            this.var5 = var5;
            return this;
        }

        public LuaEffect createLuaEffect() {
            return new LuaEffect(executionFunction, owner, var1, var2, var3, var4, var5);
        }
    }
}

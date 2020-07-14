package to.klay.wspro.core.game.scripting.lua.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.luaj.vm2.LuaFunction;
import to.klay.wspro.core.game.scripting.lua.LuaEffect;

public class EffectFactory {

    private static final Logger log = LogManager.getLogger();

    public static LuaEffect.Builder createOneShotEffect(LuaFunction execution){
        return new LuaEffect.Builder().setExecutionFunction(execution);
    }
}

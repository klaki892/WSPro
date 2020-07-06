package ton.klay.wspro.core.api.scripting;

import org.luaj.vm2.LuaValue;

public interface ScriptEngine {
    LuaValue callScript(String scriptName);

//    Class<? extends CommandExecution> getScriptedCommand(String keyword);
}

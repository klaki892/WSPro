package ton.klay.wspro.core.api.scripting;

import org.luaj.vm2.LuaValue;
import ton.klay.wspro.core.api.game.commands.CommandExecution;
import ton.klay.wspro.core.api.game.setup.GameContext;

public interface ScriptEngine {
    LuaValue callScript(String scriptName);

    Class<? extends CommandExecution> getScriptedCommand(String keyword);
}

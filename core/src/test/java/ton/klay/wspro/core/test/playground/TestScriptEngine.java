package ton.klay.wspro.core.test.playground;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.luaj.vm2.LuaValue;
import ton.klay.wspro.core.api.game.commands.CommandExecution;
import ton.klay.wspro.core.api.game.commands.CommandSender;
import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.api.scripting.ScriptEngine;

public class TestScriptEngine implements ScriptEngine {

    private static final Logger log = LogManager.getLogger();

    @Override
    public LuaValue callScript(String scriptName) {
        return null;
    }

    @Override
    public Class<? extends CommandExecution> getScriptedCommand(String keyword) {
        //TODO look for a known scripted command with the keyword
        //the list of "command scripts" will probably have to be populated when the script engine is created.

        //TODO actually implement me
        return new CommandExecution() {

            @Override
            public void execute(GameContext context, String[] args, CommandSender sender) {
                System.out.println("Hi im a script!");
            }

            @Override
            public void undo(GameContext context, String[] args) {
                //todo call undo function in a command script
                System.out.println("Hi i've been called to undo a script");
            }
        }.getClass();
    }
}

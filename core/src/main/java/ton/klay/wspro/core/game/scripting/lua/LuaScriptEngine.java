package ton.klay.wspro.core.game.scripting.lua;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;
import ton.klay.wspro.core.api.game.commands.CommandExecution;
import ton.klay.wspro.core.api.game.commands.CommandSender;
import ton.klay.wspro.core.api.game.setup.GameConfig;
import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.api.scripting.ScriptEngine;
import ton.klay.wspro.core.game.scripting.ScriptingFunctions;

import java.io.File;

/**
 * Class which defines the interaction between the game and lua user-made scripts that interact with the duel.
 */
public class LuaScriptEngine implements ScriptEngine {

    private static final Logger log = LogManager.getLogger();


    private Globals engine;

    private ScriptingFunctions scriptingFunctions;

    private final String scriptDirectory;
    private final GameConfig gameConfig;

//    /**
//     * Creates a new interface for interacting with a {@link Duel} with Lua Scripts. Instantiates the lua compilation engine for scripts to use.
//     * @param duel - the duel that this instance of the scripting engine will be used for.
//     */
//    public LuaScriptEngine(Duel duel){
//        this.duel = duel;
//        initLuaJEngine();
//    }

    public LuaScriptEngine(GameConfig gameConfig){


        this.gameConfig = gameConfig;
        //todo getScriptDirectory and actually deisng the script engine
        this.scriptDirectory = null;
        initLuaJEngine();
//        initScriptDirectory(gameConfig);
    }


    /**
     * Starts all interaction with the Luaj library
     */
    private void initLuaJEngine() {
        engine = JsePlatform.standardGlobals(); //todo - this needs to be locked down. look up how to remove all excess functionality for our global instance.

//        scriptingFunctions = duel.getScriptingFunctions();
        //todo load all enum's into the currently running engine.

        //todo instantiate and load all factories for script production into the engine

    }

    //@Override
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
                //todo call execute function in a command script, passing the arguments passed here.
            }

            @Override
            public void undo(GameContext context, String[] args) {
                //todo call undo function in a command script
            }
        }.getClass();
    }

}

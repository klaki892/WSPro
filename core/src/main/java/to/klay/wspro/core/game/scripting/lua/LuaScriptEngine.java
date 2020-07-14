package to.klay.wspro.core.game.scripting.lua;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LoadState;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.PackageLib;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JseBaseLib;
import org.luaj.vm2.lib.jse.JseMathLib;
import to.klay.wspro.core.api.cards.CardOrientation;
import to.klay.wspro.core.api.cards.GameVisibility;
import to.klay.wspro.core.api.cards.abilities.AbilityKeyword;
import to.klay.wspro.core.api.game.field.Zones;
import to.klay.wspro.core.game.Game;
import to.klay.wspro.core.game.formats.standard.cards.triggericons.StandbyTriggerIconEffect;
import to.klay.wspro.core.game.formats.standard.commands.Commands;
import to.klay.wspro.core.game.formats.standard.phases.TurnPhase;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerName;
import to.klay.wspro.core.game.scripting.lua.utilities.AbilityFactory;
import to.klay.wspro.core.game.scripting.lua.utilities.CostFactory;
import to.klay.wspro.core.game.scripting.lua.utilities.EffectFactory;

import java.lang.reflect.Array;

/**
 * Class which defines the interaction between the game and lua user-made scripts that interact with the duel.
 */
public class LuaScriptEngine {

    private static final Logger log = LogManager.getLogger();


    private Globals engine;

//    private ScriptingFunctions scriptingFunctions;

    private final String scriptDirectory;

//    /**
//     * Creates a new interface for interacting with a {@link Duel} with Lua Scripts. Instantiates the lua compilation engine for scripts to use.
//     * @param duel - the duel that this instance of the scripting engine will be used for.
//     */
//    public LuaScriptEngine(Duel duel){
//        this.duel = duel;
//        initLuaJEngine();
//    }

    private LuaScriptEngine(Globals engine){
        this.engine = engine;

        //todo getScriptDirectory and actually deisng the script engine
        this.scriptDirectory = null;
//        initScriptDirectory(gameConfig);
    }


    /**
     * Starts all interaction with the Luaj library
     */
    public static Globals initLuaJEngine(Game game) {

        Globals globals = new Globals();

        //enable math functions
        globals.load(new JseBaseLib());
        globals.load(new PackageLib());
        globals.load(new JseMathLib());

        // enable compilation from .lua files to java-bytecode.
        // Needed for executing scripts
        LoadState.install(globals);
        LuaC.install(globals);

        addScriptingLibraries(globals);
        //todo addScriptingUtilities file - contains all base scripts to be loaded

//        scriptingFunctions = duel.getScriptingFunctions();
        //todo load all enum's into the currently running engine.

        //todo instantiate and load all factories for script production into the engine

//        return new LuaScriptEngine(globals);
        return globals;
    }


    private static void addScriptingLibraries(Globals engine) {

        coerceAndSet(engine, AbilityFactory.class, "Ability");
        coerceAndSet(engine, CostFactory.class, "Cost");
        coerceAndSet(engine, EffectFactory.class, "Effect");
        coerceAndSet(engine, Commands.class);
        coerceAndSet(engine, Commands.Utilities.class);
        coerceAndSet(engine, StandbyTriggerIconEffect.class);

        //Enums
        coerceAndSet(engine, TriggerName.values());
        coerceAndSet(engine, TriggerCause.values());
        coerceAndSet(engine, Zones.values());
        coerceAndSet(engine, CardOrientation.values());
        coerceAndSet(engine, GameVisibility.values());
        coerceAndSet(engine, AbilityKeyword.values());
        coerceAndSet(engine, TurnPhase.values());


    }

    /**
     * Will Coerce the given object into a luaValue. <br/>
     * Then Sets the value in the global so it's primed for use by scripts. <br/>
     * The value set in global will be the exact name of the class that represents the object passed. <br/>
\     *
     * @param instance - a Java object which will be used for representing it's respective class in Lua Scripts.
     */
    public static void coerceAndSet(Globals globals, Object instance) {
        String className;
        if (instance instanceof Class<?>){
            className = ((Class<?>) instance).getSimpleName();
        } else {
            className = instance.getClass().getSimpleName();
        }
        //get the name of the class from the instance

        //check if its an array (read Class.getName() javadoc)
        if (instance.getClass().isArray()){
            for (int i = 0; i < Array.getLength(instance); i++) {
                coerceAndSet(globals, Array.get(instance, i));
            }
        }


        //set it with the identifier
        coerceAndSet(globals, instance, className);
    }

    /**
     * Will Coerce the given object into a luaValue. <br/>
     * Then Sets the value in the global so it's primed for use by scripts. <br/>
     *
     * @param instance      - a Java object which will be used for representing it's respective class in Lua Scripts.
     * @param luaIdentifier - the identifier that Lua Scripts will use to invoke the object.
     */
    public static void coerceAndSet(Globals globals, Object instance, String luaIdentifier) {
        LuaValue luaUserTable = CoerceJavaToLua.coerce(instance);
        globals.set(luaIdentifier, luaUserTable);
    }


}

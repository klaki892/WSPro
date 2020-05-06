package scripting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.File;
import java.net.URISyntaxException;

public class LuaTestUtil {

    private static final Logger log = LogManager.getLogger();

    public static final Globals globals = initGlobals();

    private static Globals initGlobals() {
        return JsePlatform.standardGlobals();
    }

    /**
     * Loads a LUA file into the globals
     *
     * @param path - path to file. (relative or absolute works)
     * @return a file which you can use {@link LuaValue#call()} on
     * @throws URISyntaxException
     */
    public static LuaValue getLuaFile(String path) throws URISyntaxException {
        File file = new File(LuaTestUtil.class.getResource(path).toURI());
        return globals.loadfile(file.getAbsolutePath());
    }

    /**
     * Will Coerce the given object into a luaValue. <br/>
     * Then Sets the value in the global so it's primed for use by scripts. <br/>
     * The value set in global will be the exact name of the class that represents the object passed. <br/>
     * <b> WILL NOT HANDLE ARRAYS. ONLY SINGLE OBJECTS.</b>
     *
     * @param instance - a Java object which will be used for representing it's respective class in Lua Scripts.
     */
    public static void coerceAndSet(Object instance) {
        //get the name of the class from the instance
        String fullyQName = instance.getClass().getName();
        //check if its an array (read Class.getName() javadoc)
        if (fullyQName.contains("["))
            throw new IllegalArgumentException("Cannot handle arrays.");

        //parse out the fully qualified name down to the last part.
        String className = fullyQName.substring(fullyQName.lastIndexOf('.' + 1));

        //set it with the identifier
        coerceAndSet(instance, className);
    }

    /**
     * Will Coerce the given object into a luaValue. <br/>
     * Then Sets the value in the global so it's primed for use by scripts. <br/>
     *
     * @param instance      - a Java object which will be used for representing it's respective class in Lua Scripts.
     * @param luaIdentifier - the identifier that Lua Scripts will use to invoke the object.
     */
    public static void coerceAndSet(Object instance, String luaIdentifier) {
        LuaValue luaClass = CoerceJavaToLua.coerce(instance);
        globals.set(luaIdentifier, luaClass);
    }

}

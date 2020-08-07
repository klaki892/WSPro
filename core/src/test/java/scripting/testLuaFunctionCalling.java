/*******************************************************************************
 *  WSPro - Trading Card Game Simulator
 *  Copyright (C) 2020  Klayton Killough
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published
 *  by the Free Software Foundation, version 3 of the License.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

package scripting;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;
import org.luaj.vm2.lib.jse.JsePlatform;
import scripting.helperClasses.InstanceClass;

import java.io.File;
import java.net.URISyntaxException;

public class testLuaFunctionCalling {

    public static void main(String[] args) throws URISyntaxException {

        //get the script file
        File file = new File(testLuaFunctionCalling.class.getResource("/lua/testLuaFunctionCalling.lua").toURI());

        //create the lua scripting engine instance
        Globals globals = JsePlatform.standardGlobals();

        //load the script for execution in the engine
        LuaValue luaScript = globals.loadfile(file.getAbsolutePath());

        //before running the script, define all java values that need to be known by the script
        InstanceClass iCA = InstanceClass.getInstance(1,2);
//        globals.set("mainClass", CoerceJavaToLua.coerce(iCA));
//        globals.set("mainClass", new LuaTable());

//        System.out.println("Before script " + iCA.getA());

        //custom handmade script call to create a table for the script to use
        String tableName = "mainClass";
        String customScript = (tableName + " = {};");
        globals.load(customScript).call();

        //execute the script so all functions are defined
        luaScript.call();


        //execute any functions specifically in the script
        LuaValue functionParent = globals.get(tableName);
        LuaValue functionCall = functionParent.get("retVal");
//        LuaValue functionCall = globals.get("retVal");

        System.out.println(functionCall.isfunction());
        LuaValue ret = functionCall.call();
        Integer i = (Integer)CoerceLuaToJava.coerce(ret, Integer.class);
        System.out.println(i);
        System.out.println(ret.isint());
        System.out.println(ret.isboolean());
        System.out.println(ret.istable());
        System.out.println(ret.isuserdata());

//        functionCall.call();


//        System.out.println("After script " + iCA.getA());



    }

}

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;

import java.net.URISyntaxException;

/**
 * Tests creating instances from a Factory (AKA a static class that returns instances)
 * This shows that it's possible to pass along instances of java objects to lua, and
 * call their methods without us manually defining what is inside those instances to lua.
 *
 * @see <a href="https://stackoverflow.com/a/34960596">Stack Overflow answer showing behavior</a>
 */
public class testLuaFactoryCalling {

    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args)throws URISyntaxException {

        //prep lua file to call
        LuaValue luaScript = LuaTestUtil.getLuaFile("/lua/testLuaFactoryCalling.lua");

        //setup globals for respective functions
        FactoryTest factory = new FactoryTest();

        LuaTestUtil.coerceAndSet(FactoryTest.class, "Factory");


        //call the script and load into memory
        luaScript.call();

        //get the function we will call
        LuaFunction luaMain = LuaTestUtil.globals.get("testFunction").checkfunction();

        LuaValue luaReturnObj = luaMain.call();
        luaReturnObj = luaMain.call();
        luaReturnObj = luaMain.call();

        FactoryObjectTest returnObj = (FactoryObjectTest) CoerceLuaToJava.coerce(luaReturnObj, FactoryObjectTest.class);

        System.out.println(returnObj);

    }

}

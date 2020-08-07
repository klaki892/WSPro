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
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.File;
import java.net.URISyntaxException;

public class testLuaFunctionOverload {


    public static void main(String[] args) throws URISyntaxException {

        File file = new File(testLuaFunctionOverload.class.getResource("/lua/testLuaFunctionOverload.lua").toURI());

        Globals globals = JsePlatform.standardGlobals();

        LuaValue luaScript = globals.loadfile(file.getAbsolutePath());

        TestClass testClass = new TestClass();
        testClass.setInts(100,200);

        LuaValue luaTestClass = CoerceJavaToLua.coerce(testClass);
        globals.set("TestClass", luaTestClass);

        System.out.println("Before: " + testClass.getInt1() + " " + testClass.getInt2());


        luaScript.call();
        LuaValue functionA = globals.get("luaFunctionA");
        LuaValue functionB = globals.get("luaFunctionB");
//
        functionA.call();
        System.out.println("After A: " + testClass.getInt1() + " " + testClass.getInt2());



        functionB.call();
        System.out.println("After B: " + testClass.getInt1() + " " + testClass.getInt2());
    }

    private static class TestClass {

        private int int1, int2;

        public TestClass(){
            int1 = 0;
            int2 = 0;
        }

        private void setInt1(int int1) {
            this.int1 = int1;
        }

        private void setInt2(int int2) {
            this.int2 = int2;
        }

        public int getInt1() {
            return int1;
        }

        public int getInt2() {
            return int2;
        }

        public void setInts(int int1){
            setInt1(int1);
        }

        public void setInts(int int1, int int2){
            setInt1(int1);
            setInt2(int2);
        }


    }
}

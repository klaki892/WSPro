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

public class FactoryObjectTest {

    String name;

    String val1;
    int val2;
    boolean val3;

    public FactoryObjectTest() {
        this.name = "blankname";
    }
    public FactoryObjectTest(String name) {
        this.name = name;
    }

    public void setVal1(String val1) {
        this.val1 = val1;
    }

    public void setVal2(int val2) {
        this.val2 = val2;
    }

    public void setVal3(boolean val3) {
        this.val3 = val3;
    }

    public boolean getBool() {
        return name.equals("blankname");
    }

    @Override
    public String toString() {
        return "name: " + name + "\nval1: " + val1 + "\nval2: " + val2 + "\nval3: " + val3;
    }
}

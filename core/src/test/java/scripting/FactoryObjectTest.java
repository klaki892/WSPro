package scripting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

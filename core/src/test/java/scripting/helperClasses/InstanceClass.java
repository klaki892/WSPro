package scripting.helperClasses;

public class InstanceClass {

    int a,b;
    private InstanceClass(int a, int b){
        this.a = a;
        this.b = b;
    }

    public static InstanceClass getInstance(int a , int b) {
        return new InstanceClass(a,b);
    }

    public int getA() {
        return a;
    }

    public int getB() {
        return b;
    }

    public void setA(int a) {
        this.a = a;
    }

    public void setB(int b) {
        this.b = b;
    }

    public void retVal(){
        a = 1;
    }
}

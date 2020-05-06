package scripting;

public class FactoryTest {
   /**
    * returns an {@link FactoryObjectTest} with the inital name set
    * @param name name of the object
    * @return
    */
   public static FactoryObjectTest createObj(String name){
       return new FactoryObjectTest(name);

   }
   public static FactoryObjectTest createObj(){
       return new FactoryObjectTest();

   }
}

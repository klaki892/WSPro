import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class enumGenerator {

    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args){

        String[] zoneNames = {"STOCK", "CLIMAX", "LEVEL", "HAND", "CLOCK", "STAGE", "BACK_STAGE", "CENTER_STAGE", "MEMORY", "WAITING", "DECK", "RESOLUTION", "MARKER"};

        for (String zoneName : zoneNames){
            for (String zoneName2 : zoneNames){
                if (!zoneName.equals(zoneName2)){
                    System.out.println("CONDITION_SENT_FROM_"+zoneName+"_TO_"+zoneName2+",");
                }
            }
        }

    }
}

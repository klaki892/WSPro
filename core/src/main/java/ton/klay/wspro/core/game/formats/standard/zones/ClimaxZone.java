package ton.klay.wspro.core.game.formats.standard.zones;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.field.Zones;

/**
 *  * Creates a zone based on the climax zone in the game
 *  //todo rule reference

 */
public class ClimaxZone extends StackZone {

    private static final Logger log = LogManager.getLogger();

    private static final Zones ZONE_NAME = Zones.ZONE_CLIMAX;


    public ClimaxZone(GamePlayer owner){
        super(owner, ZONE_NAME, true);
    }

    public ClimaxZone(GamePlayer owner, boolean hiddenZone){
        super(owner, ZONE_NAME, hiddenZone);
    }
}

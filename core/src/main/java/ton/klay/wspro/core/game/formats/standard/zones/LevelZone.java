package ton.klay.wspro.core.game.formats.standard.zones;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.field.Zones;

/**
 * An object that represents the level zone on a playing field.
 * //todo rules reference
 */
public class LevelZone extends StackZone {

    private static final Logger log = LogManager.getLogger();

    private static final Zones ZONE_NAME = Zones.ZONE_LEVEL;


    public LevelZone(GamePlayer owner){
        super(owner, ZONE_NAME, false);
    }

    public LevelZone(GamePlayer owner, boolean hiddenZone){
        super(owner, ZONE_NAME, hiddenZone);
    }


}

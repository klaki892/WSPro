package ton.klay.wspro.core.game.formats.standard.zones;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.field.Zones;

/**
 * An object that represents the Stock zone on a playing field.
 * //todo rules reference
 */
public class StockZone extends StackZone {

    private static final Logger log = LogManager.getLogger();

    private static final Zones ZONE_NAME = Zones.ZONE_STOCK;


    public StockZone(GamePlayer owner){
        super(owner, ZONE_NAME, true);
    }

    public StockZone(GamePlayer owner, boolean hiddenZone){
        super(owner, ZONE_NAME, hiddenZone);
    }


}

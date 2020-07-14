package ton.klay.wspro.core.game.formats.standard.zones;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.GameVisibility;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;

/**
 *  * Creates a zone based on the climax zone in the game
 *  //todo rule reference

 */
public class ClimaxZone extends PlayZone {

    private static final Logger log = LogManager.getLogger();

    private static final Zones ZONE_NAME = Zones.ZONE_CLIMAX;


    public ClimaxZone(GamePlayer owner){
        super(owner, ZONE_NAME, GameVisibility.VISIBLE_TO_ALL);
    }
}

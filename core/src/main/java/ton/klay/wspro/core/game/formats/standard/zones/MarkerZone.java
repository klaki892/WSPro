package ton.klay.wspro.core.game.formats.standard.zones;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.field.Zones;

/**
 * A Zone that represents a marker based zone on the field
 * //todo rule reference
 */
public class MarkerZone extends StackZone {

    private static final Logger log = LogManager.getLogger();

    public MarkerZone(GamePlayer owner, Zones zoneName){
        super(owner, zoneName, true);
    }
    public MarkerZone(GamePlayer owner, Zones zoneName, boolean isHiddenZone) {
        super(owner, zoneName, isHiddenZone);
    }

    //todo: The memory zone is special in that each card can have an individual state of face up and down. Implment this

}

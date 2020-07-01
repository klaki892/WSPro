package ton.klay.wspro.core.game.formats.standard.zones;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.cards.GameVisibility;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.field.Zones;

/**
 * A Zone that represents a marker based zone on the field
 * //todo rule reference
 */
public class MarkerZone extends MultiCardZone {

    private static final Logger log = LogManager.getLogger();

    public MarkerZone(GamePlayer owner, Zones zoneName){
        super(owner, zoneName, GameVisibility.HIDDEN);
    }
}

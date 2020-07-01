package ton.klay.wspro.core.game.formats.standard.zones;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.cards.GameVisibility;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.field.Zones;

/**
 * An object that represents the Memory zone on a playing field.
 * //todo rules reference
 */
public class ResolutionZone extends MultiCardZone {

    private static final Logger log = LogManager.getLogger();

    private static final Zones ZONE_NAME = Zones.ZONE_RESOLUTION;


    public ResolutionZone(GamePlayer owner) {
        super(owner, ZONE_NAME, GameVisibility.VISIBLE_TO_ALL);
    }
}

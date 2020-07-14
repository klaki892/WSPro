package ton.klay.wspro.core.game.formats.standard.zones;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.GameVisibility;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;

/**
 * Creates a zone based on being in the center stage
 * //todo rule reference
 */
public class CenterStageZone extends PlayZone {

    private static final Logger log = LogManager.getLogger();

    protected Zones[] zonesBehind = null;

    public CenterStageZone(GamePlayer owner, Zones zoneName) {
        super(owner, zoneName, GameVisibility.VISIBLE_TO_ALL);
        zonesBehind = determineBackStageZones(zoneName);
    }

    public static Zones[] determineBackStageZones(Zones zoneName){
        switch (zoneName){

            case ZONE_CENTER_STAGE_MIDDLE:
                return new Zones[]{Zones.ZONE_BACK_STAGE_RIGHT, Zones.ZONE_BACK_STAGE_LEFT};

            case ZONE_CENTER_STAGE_LEFT:
                return new Zones[]{Zones.ZONE_BACK_STAGE_LEFT};

            case ZONE_CENTER_STAGE_RIGHT:
                return new Zones[]{Zones.ZONE_BACK_STAGE_RIGHT};

            default:
                return null;
        }
    }

    /**
     * Same as {@link BackStageZone#getZonesInFront()} but with reference to the zones behind.
     * @return A list of names of the zones behind the current center stage zone.
     * @see BackStageZone#getZonesInFront()
     */
    public Zones[] getZonesBehind() {
        return zonesBehind;
    }
}

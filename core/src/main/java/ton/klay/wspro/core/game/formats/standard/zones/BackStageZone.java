package ton.klay.wspro.core.game.formats.standard.zones;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.GameVisibility;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;

/**
 * Creates a zone based on being in the back row
 * //todo rule reference
 */
public class BackStageZone extends PlayZone {

    private static final Logger log = LogManager.getLogger();

    protected Zones[] zonesInFront = null;

    public BackStageZone(GamePlayer owner, Zones zoneName) {
        super(owner, zoneName, GameVisibility.VISIBLE_TO_ALL);
        zonesInFront = determineCenterStageZones(zoneName);
    }

    public static Zones[] determineCenterStageZones(Zones zoneName){
        switch (zoneName){

            case ZONE_BACK_STAGE_LEFT:
                return new Zones[]{Zones.ZONE_CENTER_STAGE_LEFT, Zones.ZONE_CENTER_STAGE_MIDDLE};

            case ZONE_BACK_STAGE_RIGHT:
                return new Zones[]{Zones.ZONE_CENTER_STAGE_RIGHT, Zones.ZONE_CENTER_STAGE_MIDDLE};

            default:
                return null;
        }
    }

    /**
     * Returns a list of enums of the zones that are directly behind this stage. <br>
     * Useful when a reference is needed for the stages behind this one.
     * @return a list of names of the zones behind this one, but not references to the zones themselves
     * @see CenterStageZone#getZonesBehind()
     */
    public Zones[] getZonesInFront() {
        return zonesInFront;
    }
}

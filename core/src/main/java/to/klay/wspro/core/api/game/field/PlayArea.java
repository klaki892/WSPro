package to.klay.wspro.core.api.game.field;

import to.klay.wspro.core.api.game.GameRuntimeException;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.formats.standard.zones.PlayZone;

import java.util.Collection;
import java.util.EnumSet;

/**
 * Interacts with the player area of a particular player.<br/>
 * Interfaces with {@link PlayZone}'s within this area
 */
public interface PlayArea {

    /**
     * Obtains the owner of this play area
     * @return owner of the play area
     */
    GamePlayer getOwner();

    /**
     * Returns a collection of the {@link PlayZone}'s within this area, with no respect to ordering.
     * @return a collection of Play Zones, no ordering is guaranteed
     */
    Collection<PlayZone> getAllPlayZones();


    /**
     * Returns a collection of the {@link PlayZone}'s defined by the {@link EnumSet}.
     * @return a list of Play Zones, no ordering is guaranteed
     */
    Collection<PlayZone> getPlayZones(EnumSet<Zones> ZonesSet);

    /**
     * Returns an an individual Play Zone contained within the Play area.
     * @param zone  - the {@link Zones} constant representing the name of the Zone.
     * @return  -A Play Zone, provided the zone exists inside the area
     * @throws GameRuntimeException - if the zone doesnt exist inside the play area
     */
    PlayZone getPlayZone(Zones zone) throws GameRuntimeException;

    /**
     * Gets the Corresponding Marker zone when passed a Stage Zone
     * @param stageZone - an existing PlayZone on stage
     * @return Play Zone, provided the zone exists inside the area
     * @throws GameRuntimeException - if the zone doesnt correlate
     */
    public PlayZone getCorrespondingMarkerZoneOnStage(PlayZone stageZone) throws GameRuntimeException;
    
    void setOwner(GamePlayer owner);
    
}

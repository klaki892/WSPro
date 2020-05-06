package ton.klay.wspro.core.api.game.field;

import ton.klay.wspro.core.api.game.player.GamePlayer;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

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
     * Refer to {@link ZoneGroup} for common groups of the zones.
     * @return a list of Play Zones, no ordering is guaranteed
     * @see ZoneGroup
     */
    Collection<PlayZone> getPlayZones(EnumSet<Zones> ZonesSet);

    /**
     * Returns an an individual Play Zone contained within the Play area.
     * @param zone  - the {@link Zones} constant representing the name of the Zone.
     * @return  -A Play Zone, provided the zone exists inside the area
     * @throws IllegalArgumentException - if the zone doesnt exist inside the play area
     */
    PlayZone getPlayZone(Zones zone) throws IllegalArgumentException;
    
    
    void setOwner(GamePlayer owner);
    
}

package ton.klay.wspro.core.game.formats.standard.zones;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.field.PlayArea;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;

import java.util.*;

public class StandardWeissPlayArea implements PlayArea {

    private static final Logger log = LogManager.getLogger();
    private GamePlayer owner;
    private final Map<Zones, PlayZone> zonesMap;

    public StandardWeissPlayArea(GamePlayer owner) {
        this.owner = owner;
        zonesMap = new EnumMap<>(Zones.class);

        zonesMap.put(Zones.ZONE_DECK, new DeckZone(owner));
        zonesMap.put(Zones.ZONE_HAND, new HandZone(owner));
        zonesMap.put(Zones.ZONE_WAITING_ROOM, new WaitingRoomZone(owner));
        zonesMap.put(Zones.ZONE_CLOCK, new ClockZone(owner));
        zonesMap.put(Zones.ZONE_LEVEL, new LevelZone(owner));
        zonesMap.put(Zones.ZONE_STOCK, new StockZone(owner));
        zonesMap.put(Zones.ZONE_CLIMAX, new ClimaxZone(owner));
        zonesMap.put(Zones.ZONE_MEMORY, new MemoryZone(owner));
        zonesMap.put(Zones.ZONE_RESOLUTION, new ResolutionZone(owner));

        zonesMap.put(Zones.ZONE_CENTER_STAGE_LEFT, new CenterStageZone(owner, Zones.ZONE_CENTER_STAGE_LEFT));
        zonesMap.put(Zones.ZONE_CENTER_STAGE_MIDDLE, new CenterStageZone(owner, Zones.ZONE_CENTER_STAGE_MIDDLE));
        zonesMap.put(Zones.ZONE_CENTER_STAGE_RIGHT, new CenterStageZone(owner, Zones.ZONE_CENTER_STAGE_RIGHT));
        zonesMap.put(Zones.ZONE_BACK_STAGE_LEFT, new BackStageZone(owner, Zones.ZONE_BACK_STAGE_LEFT));
        zonesMap.put(Zones.ZONE_BACK_STAGE_RIGHT, new BackStageZone(owner, Zones.ZONE_BACK_STAGE_RIGHT));

        zonesMap.put(Zones.ZONE_CENTER_STAGE_LEFT_MARKER, new MarkerZone(owner, Zones.ZONE_CENTER_STAGE_LEFT_MARKER));
        zonesMap.put(Zones.ZONE_CENTER_STAGE_MIDDLE_MARKER, new MarkerZone(owner, Zones.ZONE_CENTER_STAGE_MIDDLE_MARKER));
        zonesMap.put(Zones.ZONE_CENTER_STAGE_RIGHT_MARKER, new MarkerZone(owner, Zones.ZONE_CENTER_STAGE_RIGHT_MARKER));
        zonesMap.put(Zones.ZONE_BACK_STAGE_LEFT_MARKER, new MarkerZone(owner, Zones.ZONE_BACK_STAGE_LEFT_MARKER));
        zonesMap.put(Zones.ZONE_BACK_STAGE_RIGHT_MARKER, new MarkerZone(owner, Zones.ZONE_BACK_STAGE_RIGHT_MARKER));

    }

    @Override
    public GamePlayer getOwner() {
        return owner;
    }

    @Override
    public Collection<PlayZone> getAllPlayZones() {
        return zonesMap.values();
    }

    @Override
    public Collection<PlayZone> getPlayZones(EnumSet<Zones> zoneSet) {
        Collection<PlayZone> retZones = new ArrayList<>();
        for (Zones zone : zoneSet) {
            retZones.add(getPlayZone(zone));
        }
        return retZones;
    }


    @Override
    public PlayZone getPlayZone(Zones zone) {
        if (zonesMap.containsKey(zone))
            return zonesMap.get(zone);
        else
            throw new IllegalArgumentException("Play area didnt contain a specific zone.");
    }


    @Override
    public void setOwner(GamePlayer owner) {
        this.owner = owner;
    }
}

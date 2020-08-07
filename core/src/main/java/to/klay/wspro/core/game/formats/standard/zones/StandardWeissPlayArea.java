/*******************************************************************************
 *  WSPro - Trading Card Game Simulator
 *  Copyright (C) 2020  Klayton Killough
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published
 *  by the Free Software Foundation, version 3 of the License.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

package to.klay.wspro.core.game.formats.standard.zones;

import net.badata.protobuf.converter.annotation.ProtoClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.game.GameRuntimeException;
import to.klay.wspro.core.api.game.field.PlayArea;
import to.klay.wspro.core.api.game.field.Zones;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.proto.ProtoPlayZone;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;

import static to.klay.wspro.core.api.cards.GameVisibility.HIDDEN;
import static to.klay.wspro.core.api.cards.GameVisibility.VISIBLE_TO_ALL;
import static to.klay.wspro.core.api.cards.GameVisibility.VISIBLE_TO_MASTER;
import static to.klay.wspro.core.api.game.field.Zones.ZONE_BACK_STAGE_LEFT;
import static to.klay.wspro.core.api.game.field.Zones.ZONE_BACK_STAGE_LEFT_MARKER;
import static to.klay.wspro.core.api.game.field.Zones.ZONE_BACK_STAGE_RIGHT;
import static to.klay.wspro.core.api.game.field.Zones.ZONE_BACK_STAGE_RIGHT_MARKER;
import static to.klay.wspro.core.api.game.field.Zones.ZONE_CENTER_STAGE_LEFT;
import static to.klay.wspro.core.api.game.field.Zones.ZONE_CENTER_STAGE_LEFT_MARKER;
import static to.klay.wspro.core.api.game.field.Zones.ZONE_CENTER_STAGE_MIDDLE;
import static to.klay.wspro.core.api.game.field.Zones.ZONE_CENTER_STAGE_MIDDLE_MARKER;
import static to.klay.wspro.core.api.game.field.Zones.ZONE_CENTER_STAGE_RIGHT;
import static to.klay.wspro.core.api.game.field.Zones.ZONE_CENTER_STAGE_RIGHT_MARKER;
import static to.klay.wspro.core.api.game.field.Zones.ZONE_CLIMAX;
import static to.klay.wspro.core.api.game.field.Zones.ZONE_CLOCK;
import static to.klay.wspro.core.api.game.field.Zones.ZONE_DECK;
import static to.klay.wspro.core.api.game.field.Zones.ZONE_HAND;
import static to.klay.wspro.core.api.game.field.Zones.ZONE_LEVEL;
import static to.klay.wspro.core.api.game.field.Zones.ZONE_MEMORY;
import static to.klay.wspro.core.api.game.field.Zones.ZONE_RESOLUTION;
import static to.klay.wspro.core.api.game.field.Zones.ZONE_STOCK;
import static to.klay.wspro.core.api.game.field.Zones.ZONE_WAITING_ROOM;

@ProtoClass(ProtoPlayZone.class)
public class StandardWeissPlayArea implements PlayArea {

    private static final Logger log = LogManager.getLogger();
    private GamePlayer owner;
    private final Map<Zones, PlayZone> zonesMap;

    public StandardWeissPlayArea(GamePlayer owner) {
        this.owner = owner;
        zonesMap = new EnumMap<>(Zones.class);

        zonesMap.put(ZONE_DECK, new PlayZone(owner, ZONE_DECK, HIDDEN));
        zonesMap.put(ZONE_HAND, new PlayZone(owner, ZONE_HAND, VISIBLE_TO_MASTER));
        zonesMap.put(ZONE_WAITING_ROOM, new PlayZone(owner, ZONE_WAITING_ROOM, VISIBLE_TO_ALL));
        zonesMap.put(ZONE_CLOCK, new PlayZone(owner, ZONE_CLOCK, VISIBLE_TO_ALL));
        zonesMap.put(ZONE_LEVEL, new PlayZone(owner, ZONE_LEVEL, VISIBLE_TO_ALL));
        zonesMap.put(ZONE_STOCK, new PlayZone(owner, ZONE_STOCK, HIDDEN));
        zonesMap.put(ZONE_CLIMAX, new PlayZone(owner, ZONE_CLIMAX, VISIBLE_TO_ALL));
        zonesMap.put(ZONE_MEMORY, new PlayZone(owner, ZONE_MEMORY, VISIBLE_TO_ALL));
        zonesMap.put(ZONE_RESOLUTION, new PlayZone(owner, ZONE_RESOLUTION, VISIBLE_TO_ALL));
        
        zonesMap.put(ZONE_CENTER_STAGE_LEFT, new PlayZone(owner, ZONE_CENTER_STAGE_LEFT, VISIBLE_TO_ALL));
        zonesMap.put(ZONE_CENTER_STAGE_MIDDLE, new PlayZone(owner, ZONE_CENTER_STAGE_MIDDLE, VISIBLE_TO_ALL));
        zonesMap.put(ZONE_CENTER_STAGE_RIGHT, new PlayZone(owner, ZONE_CENTER_STAGE_RIGHT, VISIBLE_TO_ALL));
        zonesMap.put(ZONE_BACK_STAGE_LEFT, new PlayZone(owner, ZONE_BACK_STAGE_LEFT, VISIBLE_TO_ALL));
        zonesMap.put(ZONE_BACK_STAGE_RIGHT, new PlayZone(owner, ZONE_BACK_STAGE_RIGHT, VISIBLE_TO_ALL));

        zonesMap.put(ZONE_CENTER_STAGE_LEFT_MARKER, new PlayZone(owner, ZONE_CENTER_STAGE_LEFT_MARKER, HIDDEN));
        zonesMap.put(ZONE_CENTER_STAGE_MIDDLE_MARKER, new PlayZone(owner, ZONE_CENTER_STAGE_MIDDLE_MARKER, HIDDEN));
        zonesMap.put(ZONE_CENTER_STAGE_RIGHT_MARKER, new PlayZone(owner, ZONE_CENTER_STAGE_RIGHT_MARKER, HIDDEN));
        zonesMap.put(ZONE_BACK_STAGE_LEFT_MARKER, new PlayZone(owner, ZONE_BACK_STAGE_LEFT_MARKER, HIDDEN));
        zonesMap.put(ZONE_BACK_STAGE_RIGHT_MARKER, new PlayZone(owner, ZONE_BACK_STAGE_RIGHT_MARKER, HIDDEN));
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
            throw new GameRuntimeException("Play area didnt contain a specific zone.");
    }

    public PlayZone getCorrespondingMarkerZoneOnStage(PlayZone stageZone){
        switch (stageZone.getZoneName()){
            case ZONE_CENTER_STAGE_LEFT:
                return getPlayZone(ZONE_CENTER_STAGE_LEFT_MARKER);
            case ZONE_CENTER_STAGE_MIDDLE:
                return getPlayZone(ZONE_CENTER_STAGE_MIDDLE_MARKER);
            case ZONE_CENTER_STAGE_RIGHT:
                return getPlayZone(ZONE_CENTER_STAGE_RIGHT_MARKER);
            case ZONE_BACK_STAGE_LEFT:
                return getPlayZone(ZONE_BACK_STAGE_LEFT_MARKER);
            case ZONE_BACK_STAGE_RIGHT:
                return getPlayZone(ZONE_BACK_STAGE_RIGHT_MARKER);
            default:
            throw new GameRuntimeException("Asked to obtain marker for zone " + stageZone.getZoneName().name());
        }
    }


    @Override
    public void setOwner(GamePlayer owner) {
        this.owner = owner;
    }
}

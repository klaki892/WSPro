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

package to.klay.wspro.core.game.actions;

import com.google.common.base.MoreObjects;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.formats.standard.zones.PlayZone;
import to.klay.wspro.core.game.proto.ProtoPlayZonePair;

import java.util.Arrays;
import java.util.List;

@ProtoClass(ProtoPlayZonePair.class)
public class PlayZonePair {

    private static final Logger log = LogManager.getLogger();

    @ProtoField
    private final PlayZone firstZone;
    @ProtoField
    private final PlayZone secondZone;

    public PlayZonePair(PlayZone firstZone, PlayZone secondZone){

        this.firstZone = firstZone;
        this.secondZone = secondZone;
    }

    public PlayZone getFirstZone() {
        return firstZone;
    }

    public PlayZone getSecondZone() {
        return secondZone;
    }

    public List<PlayZone> getBothZones(){
        return Arrays.asList(firstZone, secondZone);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("firstZone", firstZone)
                .add("secondZone", secondZone)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayZonePair that = (PlayZonePair) o;
        return (firstZone.equals(that.firstZone) && secondZone.equals(that.secondZone) ||
                firstZone.equals(that.secondZone) && secondZone.equals(that.firstZone));
    }

    @Override
    public int hashCode() {
        return firstZone.hashCode() + secondZone.hashCode();
    }
}

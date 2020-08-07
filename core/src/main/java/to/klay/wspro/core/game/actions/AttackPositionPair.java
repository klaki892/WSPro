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
import com.google.common.base.Objects;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.formats.standard.zones.PlayZone;
import to.klay.wspro.core.game.proto.AttackTypeProtoTypeConverter;
import to.klay.wspro.core.game.proto.ProtoAttackPositionPair;

@ProtoClass(ProtoAttackPositionPair.class)
public class AttackPositionPair {

    private static final Logger log = LogManager.getLogger();
    @ProtoField(converter = AttackTypeProtoTypeConverter.class)
    private final AttackType attackType;
    @ProtoField
    private final PlayZone zone;

    public AttackPositionPair(AttackType attackType, PlayZone zone) {
        this.attackType = attackType;
        this.zone = zone;
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public PlayZone getZone() {
        return zone;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("attackType", attackType)
                .add("zone", zone)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttackPositionPair that = (AttackPositionPair) o;
        return attackType == that.attackType &&
                Objects.equal(zone, that.zone);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attackType, zone);
    }
}


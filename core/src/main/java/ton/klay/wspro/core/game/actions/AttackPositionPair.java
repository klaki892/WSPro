package ton.klay.wspro.core.game.actions;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.proto.ProtoAttackPositionPair;
import ton.klay.wspro.core.game.formats.standard.zones.PlayZone;
import ton.klay.wspro.core.game.proto.AttackTypeProtoTypeConverter;

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


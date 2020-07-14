package ton.klay.wspro.core.game.actions;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.game.formats.standard.zones.PlayZone;

public class AttackPositionPair {

    private static final Logger log = LogManager.getLogger();
    private final AttackType attackType;
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


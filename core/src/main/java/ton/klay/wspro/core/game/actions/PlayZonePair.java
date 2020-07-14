package ton.klay.wspro.core.game.actions;

import com.google.common.base.MoreObjects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.game.formats.standard.zones.PlayZone;

import java.util.Arrays;
import java.util.List;

public class PlayZonePair {

    private static final Logger log = LogManager.getLogger();
    private final PlayZone firstZone;
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

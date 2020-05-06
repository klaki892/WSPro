package ton.klay.wspro.core.api.game.field;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumSet;

public class ZoneGroup {

    private static final Logger log = LogManager.getLogger();

    private ZoneGroup(){}

    public static final EnumSet<Zones> CENTER_STAGE = EnumSet.of(Zones.ZONE_CENTER_STAGE_LEFT, Zones.ZONE_CENTER_STAGE_MIDDLE, Zones.ZONE_CENTER_STAGE_RIGHT);
    public static final EnumSet<Zones> BACK_STAGE = EnumSet.of(Zones.ZONE_BACK_STAGE_LEFT, Zones.ZONE_BACK_STAGE_RIGHT);
    public static final EnumSet<Zones> STAGE = EnumSet.of(Zones.ZONE_CENTER_STAGE_LEFT, Zones.ZONE_CENTER_STAGE_MIDDLE, Zones.ZONE_CENTER_STAGE_RIGHT,
            Zones.ZONE_BACK_STAGE_LEFT, Zones.ZONE_BACK_STAGE_RIGHT);
    public static final EnumSet<Zones> CENTER_STAGE_MARKER = EnumSet.of(Zones.ZONE_CENTER_STAGE_LEFT_MARKER, Zones.ZONE_CENTER_STAGE_MIDDLE_MARKER, Zones.ZONE_CENTER_STAGE_RIGHT_MARKER);
    public static final EnumSet<Zones> BACK_STAGE_MARKER = EnumSet.of(Zones.ZONE_BACK_STAGE_LEFT_MARKER, Zones.ZONE_BACK_STAGE_RIGHT_MARKER);
    public static final EnumSet<Zones> STAGE_MARKER = EnumSet.of(Zones.ZONE_CENTER_STAGE_LEFT_MARKER, Zones.ZONE_CENTER_STAGE_MIDDLE_MARKER, Zones.ZONE_CENTER_STAGE_RIGHT_MARKER,
            Zones.ZONE_BACK_STAGE_LEFT_MARKER, Zones.ZONE_BACK_STAGE_RIGHT_MARKER);
}

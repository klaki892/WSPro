package to.klay.wspro.core.api.game.field;

import to.klay.wspro.core.game.formats.standard.zones.PlayZone;

import java.util.EnumSet;

/**
 * The play Zones in Weiss
 * @see <code>Weiss Schwarz Rule 3</code>
 */
public enum Zones {
    ZONE_DECK,
    ZONE_HAND,
    ZONE_WAITING_ROOM,
    ZONE_CLOCK,
    ZONE_LEVEL,
    ZONE_STOCK,
    ZONE_CLIMAX,
    ZONE_MEMORY,
    ZONE_RESOLUTION,

    ZONE_CENTER_STAGE_LEFT,
    ZONE_CENTER_STAGE_MIDDLE,
    ZONE_CENTER_STAGE_RIGHT,
    ZONE_BACK_STAGE_LEFT,
    ZONE_BACK_STAGE_RIGHT,
    ZONE_CENTER_STAGE_LEFT_MARKER,
    ZONE_CENTER_STAGE_MIDDLE_MARKER,
    ZONE_CENTER_STAGE_RIGHT_MARKER,
    ZONE_BACK_STAGE_LEFT_MARKER,
    ZONE_BACK_STAGE_RIGHT_MARKER;

    public static final EnumSet<Zones> ZONE_CENTER_STAGE = EnumSet.of(Zones.ZONE_CENTER_STAGE_LEFT, Zones.ZONE_CENTER_STAGE_MIDDLE, Zones.ZONE_CENTER_STAGE_RIGHT);
    public static final EnumSet<Zones> ZONE_BACK_STAGE = EnumSet.of(Zones.ZONE_BACK_STAGE_LEFT, Zones.ZONE_BACK_STAGE_RIGHT);
    public static final EnumSet<Zones> ZONE_STAGE = EnumSet.of(Zones.ZONE_CENTER_STAGE_LEFT, Zones.ZONE_CENTER_STAGE_MIDDLE, Zones.ZONE_CENTER_STAGE_RIGHT,
            Zones.ZONE_BACK_STAGE_LEFT, Zones.ZONE_BACK_STAGE_RIGHT);
    public static final EnumSet<Zones> ZONE_CENTER_STAGE_MARKER = EnumSet.of(Zones.ZONE_CENTER_STAGE_LEFT_MARKER, Zones.ZONE_CENTER_STAGE_MIDDLE_MARKER, Zones.ZONE_CENTER_STAGE_RIGHT_MARKER);
    public static final EnumSet<Zones> ZONE_BACK_STAGE_MARKER = EnumSet.of(Zones.ZONE_BACK_STAGE_LEFT_MARKER, Zones.ZONE_BACK_STAGE_RIGHT_MARKER);
    public static final EnumSet<Zones> ZONE_STAGE_MARKER = EnumSet.of(Zones.ZONE_CENTER_STAGE_LEFT_MARKER, Zones.ZONE_CENTER_STAGE_MIDDLE_MARKER, Zones.ZONE_CENTER_STAGE_RIGHT_MARKER,
            Zones.ZONE_BACK_STAGE_LEFT_MARKER, Zones.ZONE_BACK_STAGE_RIGHT_MARKER);

    public static boolean isOnStage(PlayZone zone){
        return ZONE_STAGE.contains(zone.getZoneName());
    }
    public static boolean isOnBackStage(PlayZone zone){
        return ZONE_BACK_STAGE.contains(zone.getZoneName());
    }
    public static boolean isOnCenterStage(PlayZone zone){
        return ZONE_CENTER_STAGE.contains(zone.getZoneName());
    }


}

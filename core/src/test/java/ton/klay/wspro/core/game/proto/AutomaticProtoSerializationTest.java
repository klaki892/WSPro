package ton.klay.wspro.core.game.proto;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import to.klay.wspro.core.game.proto.GameMessageProto;
import ton.klay.wspro.core.api.cards.GameVisibility;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import ton.klay.wspro.core.game.formats.standard.triggers.ZoneShuffledTrigger;
import ton.klay.wspro.core.game.formats.standard.zones.PlayZone;

import java.util.Collections;

public class AutomaticProtoSerializationTest {

    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args) {
        AutomaticProtoSerializationTest automaticProtoSerializationTest = new AutomaticProtoSerializationTest();
        automaticProtoSerializationTest.testProtoSerialization();
    }

    @Test
    public void testProtoSerialization(){
        PlayZone zone = new PlayZone(null, Zones.ZONE_DECK, GameVisibility.HIDDEN);
        ZoneShuffledTrigger gameTrigger = new ZoneShuffledTrigger(zone, Collections.emptyList(),
                TriggerCause.GAME_ACTION, null);


        GameMessageProto gameMessageProto = gameTrigger.serializeToProto();


        Assertions.assertTrue(gameMessageProto.hasTrigger());
        Assertions.assertTrue(gameMessageProto.getTrigger().hasZoneShuffledTrigger());
        Assertions.assertEquals(gameMessageProto.getTrigger().getZoneShuffledTrigger().getTriggerName().ordinal(), gameTrigger.getTriggerName().ordinal());
    }
}

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

package to.klay.wspro.core.game.proto;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import to.klay.wspro.core.api.cards.GameVisibility;
import to.klay.wspro.core.api.game.field.Zones;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import to.klay.wspro.core.game.formats.standard.triggers.ZoneShuffledTrigger;
import to.klay.wspro.core.game.formats.standard.zones.PlayZone;

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

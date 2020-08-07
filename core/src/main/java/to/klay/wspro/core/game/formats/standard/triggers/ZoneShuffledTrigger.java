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

package to.klay.wspro.core.game.formats.standard.triggers;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.game.GameEntity;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.zones.PlayZone;
import to.klay.wspro.core.game.proto.ProtoZoneShuffledTrigger;
import to.klay.wspro.core.game.proto.TriggerNameTypeConverter;

import java.util.List;

@ProtoClass(ProtoZoneShuffledTrigger.class)
public final class ZoneShuffledTrigger extends BaseTrigger{

    private static final Logger log = LogManager.getLogger();
    @ProtoField
    private final PlayZone zone;
    @ProtoField
    private final List<PlayingCard> cards;
    @ProtoField(converter = TriggerNameTypeConverter.class)
    private final TriggerName triggerName = TriggerName.ZONE_SHUFFLED;

    public ZoneShuffledTrigger(PlayZone zone, List<PlayingCard> cards, TriggerCause cause, GameEntity caller) {

        super(cause, caller);
        this.zone = zone;
        this.cards = cards;
    }


    @Override
    public TriggerName getTriggerName() {
        return triggerName;
    }

    public PlayZone getZone() {
        return zone;
    }

    public List<PlayingCard> getCards() {
        return cards;
    }

//    @Override
//    public GameMessageProto serializeToProto() {
//        GameTriggerProto triggerProto = GameTriggerProto.newBuilder()
//                .setZoneShuffledTrigger(
//                        Converter.create().toProtobuf(ProtoZoneShuffledTrigger.class, this)
//                ).build();
//        return GameMessageProto.newBuilder().setTrigger(triggerProto).build();
//    }
}

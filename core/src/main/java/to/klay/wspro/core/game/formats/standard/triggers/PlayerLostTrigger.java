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
import to.klay.wspro.core.api.game.LoseConditions;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.proto.LoseConditionsProtoTypeConverter;
import to.klay.wspro.core.game.proto.ProtoPlayerLostTrigger;

@ProtoClass(ProtoPlayerLostTrigger.class)
public class PlayerLostTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    @ProtoField
    private final GamePlayer player;
    @ProtoField(converter = LoseConditionsProtoTypeConverter.class)
    private final LoseConditions loseCondition;

    public PlayerLostTrigger(GamePlayer player, LoseConditions loseCondition, TriggerCause cause, GameEntity caller) {
        super(cause, caller);
        this.player = player;
        this.loseCondition = loseCondition;
    }

    public GamePlayer getPlayer() {
        return player;
    }

    public LoseConditions getLoseCondition() {
        return loseCondition;
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.PLAYER_LOST;
    }
}

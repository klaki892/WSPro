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

package to.klay.wspro.core.game.events;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import to.klay.wspro.core.api.game.GameEntity;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.Game;
import to.klay.wspro.core.game.proto.ProtoInterruptRuleAction;

/**
 * @author Klayton Killough
 * Date: 8/13/2017
 * An Interrupt-Type Rule Action Which halts gameplay to perform the action
 */
@ProtoClass(ProtoInterruptRuleAction.class)
public abstract class InterruptRuleAction implements GameEntity, RuleAction {

    @ProtoField
    protected final GamePlayer player;

    protected final transient Game game;

    public InterruptRuleAction(GamePlayer player){
        this.player = player;
        game = player.getGame();
    }

    @Override
    public GamePlayer getMaster() {
        return player;
    }
}

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
import to.klay.wspro.core.game.actions.AttackType;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.proto.AttackTypeProtoTypeConverter;
import to.klay.wspro.core.game.proto.ProtoWillAttackTrigger;

/**
 * indicates a player has decided to perform an attack with a particular character
 */
@ProtoClass(ProtoWillAttackTrigger.class)
public class WillAttackTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    @ProtoField
    private final PlayingCard attackingCard;
    @ProtoField(converter = AttackTypeProtoTypeConverter.class)
    private final AttackType attackType;

    public WillAttackTrigger(PlayingCard attackingCard, AttackType attackType, TriggerCause cause, GameEntity caller) {
    super(cause, caller);
        this.attackingCard = attackingCard;
        this.attackType = attackType;
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.WILL_ATTACK;
    }

    public PlayingCard getAttackingCard() {
        return attackingCard;
    }

    public AttackType getAttackType() {
        return attackType;
    }
}

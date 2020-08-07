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

package to.klay.wspro.core.game.effects;

import to.klay.wspro.core.api.game.GameRuntimeException;
import to.klay.wspro.core.game.actions.AttackType;
import to.klay.wspro.core.game.cardLogic.ability.Ability;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;

public class AttackTypeSoulChangeEffect extends SoulChangeContinuousEffect {

    protected final int startingTurnNumber;
    private final AttackType attackType;
    private final int sideSoulChangeAmount;
    protected int fundamentalOrder;

    public AttackTypeSoulChangeEffect(PlayingCard card, AttackType attackType,
                                      int currentTurn, int soulChangeAmount){
        super(card);
        this.attackType = attackType;
        this.sideSoulChangeAmount = soulChangeAmount;
        startingTurnNumber = currentTurn;

        //this assumes the effect immediately happens upon instantiation
        fundamentalOrder = game.getNextFundamentalOrder();
    }

    @Override
    public void execute(Object... vars) {
        if (attackType == AttackType.DIRECT) {
            card.setSoul(card.getSoul()+1);
        } else if (attackType == AttackType.SIDE){
            card.setSoul(card.getSoul()- sideSoulChangeAmount);
        } else {
            throw new GameRuntimeException(new IllegalArgumentException(
                    "Attack Type Soul change was not given a correct type. Got:" + attackType));
        }
        //todo announce stat change? or maybe this is the job of PlayingCard
    }

    @Override
    public Ability getOwningAbility() {
        return null; //weiss rules dont define this
    }

    @Override
    public void setFundamentalOrder(int orderNumber) {
        this.fundamentalOrder = orderNumber;
    }

    @Override
    public int getFundamentalOrder() {
        return fundamentalOrder;
    }

    @Override
    public boolean meetsCondition() {
        //lasts until end of turn
        return game.getCurrentTurnNumber() == startingTurnNumber;
    }

    @Override
    public boolean shouldUnregister() {
        return true; //when conditional isnt met, true
    }

    @Override
    public boolean isDependent() {
        return false; //doesnt care about card stats
    }
}
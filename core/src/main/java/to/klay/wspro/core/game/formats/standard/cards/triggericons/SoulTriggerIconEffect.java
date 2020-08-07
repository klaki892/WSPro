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

package to.klay.wspro.core.game.formats.standard.cards.triggericons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.cardLogic.ability.Ability;
import to.klay.wspro.core.game.effects.SoulChangeContinuousEffect;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;

public class SoulTriggerIconEffect extends SoulChangeContinuousEffect {

    private static final Logger log = LogManager.getLogger();
    private final PlayingCard applicationCard;
    private final int startingTurn;
    private int fundamentalOrder;
    public SoulTriggerIconEffect(PlayingCard triggerCard, PlayingCard attackingCard) {
        super(triggerCard);
        this.applicationCard = attackingCard;
        startingTurn = game.getCurrentTurnNumber();

        fundamentalOrder = game.getNextFundamentalOrder();
    }

    @Override
    public boolean meetsCondition() {
        return game.getCurrentTurnNumber() == startingTurn;
    }

    @Override
    public boolean shouldUnregister() {
        return true; //duration ended
    }

    @Override
    public boolean isDependent() {
        return false; //blindly applies effect
    }

    @Override
    public void execute(Object... vars) {
        applicationCard.setSoul(applicationCard.getSoul() + 1);
    }

    @Override
    public Ability getOwningAbility() {
        return null; //doesnt have an owning ability
    }

    @Override
    public void setFundamentalOrder(int orderNumber) {
        fundamentalOrder = orderNumber;
    }

    @Override
    public int getFundamentalOrder() {
        return fundamentalOrder;
    }
}

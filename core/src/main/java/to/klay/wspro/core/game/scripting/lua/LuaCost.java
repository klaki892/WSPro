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

package to.klay.wspro.core.game.scripting.lua;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.Cost;
import to.klay.wspro.core.api.cards.CostConditional;
import to.klay.wspro.core.api.cards.PayCost;
import to.klay.wspro.core.api.game.GameEntity;

public class LuaCost implements Cost {

    private static final Logger log = LogManager.getLogger();
    private GameEntity costOwner;
    private CostConditional conditional;
    private PayCost payCost;

    public LuaCost(GameEntity costOwner, CostConditional conditional, PayCost payCost){
        this.costOwner = costOwner;

        this.conditional = conditional;
        this.payCost = payCost;
    }

    @Override
    public void setCostConditional(CostConditional conditional) {
        this.conditional = conditional;
    }

    @Override
    public boolean isPayable() {
        return conditional.isDoable();
    }

    @Override
    public void setCostAction(PayCost payCost) {
        this.payCost = payCost;
    }

    @Override
    public void payCost() {
        payCost.execute();
    }

    public GameEntity getCostOwner() {
        return costOwner;
    }

    public void setCostOwner(GameEntity costOwner) {
        this.costOwner = costOwner;
    }

    public static class Builder {
        private GameEntity costOwner;
        private CostConditional conditional;
        private PayCost payCost;

        public Builder setConditional(CostConditional conditional) {
            this.conditional = conditional;
            return this;
        }

        public Builder setPayCost(PayCost payCost) {
            this.payCost = payCost;
            return this;
        }

        public Builder setCostOwner(GameEntity costOwner) {
            this.costOwner = costOwner;
            return this;
        }

        public LuaCost createLuaCost() {
            return new LuaCost(costOwner, conditional, payCost);
        }
    }
}

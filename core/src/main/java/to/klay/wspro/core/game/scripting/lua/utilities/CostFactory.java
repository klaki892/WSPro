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

package to.klay.wspro.core.game.scripting.lua.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.cards.StockCost;
import to.klay.wspro.core.game.scripting.lua.LuaCost;

public class CostFactory {

    private static final Logger log = LogManager.getLogger();

    public static LuaCost.Builder createStockCost(PlayingCard card, int stockCount){
        StockCost stockCost = new StockCost.Builder()
                .setCostCount(stockCount)
                .setOwner(card)
                .createStockCost();

        return new LuaCost.Builder()
                .setConditional(stockCost.getCostConditional())
                .setPayCost(stockCost.getPayCost());
    }

}

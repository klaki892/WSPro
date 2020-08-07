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

package to.klay.wspro.core.game.formats.standard.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.CardOrientation;
import to.klay.wspro.core.api.cards.Cost;
import to.klay.wspro.core.api.cards.CostConditional;
import to.klay.wspro.core.api.cards.PayCost;
import to.klay.wspro.core.api.game.GameEntity;
import to.klay.wspro.core.api.game.field.Zones;
import to.klay.wspro.core.game.formats.standard.commands.Commands;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerName;
import to.klay.wspro.core.game.formats.standard.zones.PlayZone;

/**
 * A Cost that consists of Moving stock cards to the waiting room
 */
public class StockCost implements Cost {

    private static final Logger log = LogManager.getLogger();
    private final GameEntity owner;
    private final int costCount;
    private CostConditional costConditional;
    private PayCost payCost;

    public StockCost(GameEntity owner, int costCount){
        this.owner = owner;
        this.costCount = costCount;

        PlayZone stockZone = owner.getMaster().getPlayArea().getPlayZone(Zones.ZONE_STOCK);
        PlayZone waitingRoom = owner.getMaster().getPlayArea().getPlayZone(Zones.ZONE_WAITING_ROOM);
        costConditional = () -> stockZone.size() >= costCount;
         payCost = () -> {
            //if cost doesnt exist, we dont do anything
            if (costCount <= 0) return;

            for (int i = 0; i < costCount; i++) {
                if (!owner.getMaster().getGame().getTimingManager().replaceableAction(TriggerName.WILL_PAY_STOCK)) {
                    PlayingCard topCard = stockZone.getContents().get(Commands.Utilities.getTopOfZoneIndex(stockZone));
                    Commands.moveCard(topCard, stockZone, waitingRoom, Commands.Utilities.getTopOfZoneIndex(waitingRoom),
                            CardOrientation.STAND, waitingRoom.getVisibility(), TriggerCause.GAME_ACTION, owner);
                }
            }
        };

    }


    @Override
    public boolean isPayable() {
        return costConditional.isDoable();
    }

    @Override
    public void setCostAction(PayCost payCost) {
        this.payCost = payCost;
    }

    @Override
    public void payCost() {
        payCost.execute();
    }

    public void setCostConditional(CostConditional costConditional) {
        this.costConditional = costConditional;
    }

    public GameEntity getOwner() {
        return owner;
    }

    public int getCostCount() {
        return costCount;
    }

    public CostConditional getCostConditional() {
        return costConditional;
    }

    public PayCost getPayCost() {
        return payCost;
    }

    public static class Builder {
        private GameEntity owner;
        private int costCount;

        public Builder setOwner(GameEntity owner) {
            this.owner = owner;
            return this;
        }

        public Builder setCostCount(int costCount) {
            this.costCount = costCount;
            return this;
        }

        public StockCost createStockCost() {
            return new StockCost(owner, costCount);
        }
    }
}

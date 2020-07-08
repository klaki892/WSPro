package ton.klay.wspro.core.game.scripting.lua;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.Cost;
import ton.klay.wspro.core.api.cards.CostConditional;
import ton.klay.wspro.core.api.cards.PayCost;
import ton.klay.wspro.core.api.game.GameEntity;

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

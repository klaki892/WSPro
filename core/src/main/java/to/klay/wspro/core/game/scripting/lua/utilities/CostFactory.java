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

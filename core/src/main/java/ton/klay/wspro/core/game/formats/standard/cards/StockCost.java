package ton.klay.wspro.core.game.formats.standard.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.CardOrientation;
import ton.klay.wspro.core.api.cards.Cost;
import ton.klay.wspro.core.api.cards.CostConditional;
import ton.klay.wspro.core.api.cards.PayCost;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.game.Game;
import ton.klay.wspro.core.game.formats.standard.commands.Commands;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerCause;

/**
 * A Cost that consists of Moving stock cards to the waiting room
 */
public class StockCost implements Cost {

    private static final Logger log = LogManager.getLogger();
    private final Game game;
    private final GameEntity owner;
    private final int costCount;
    private CostConditional costConditional;
    private PayCost payCost;

    public StockCost(GameEntity owner, int costCount){
        this.game = owner.getMaster().getGame();
        this.owner = owner;
        this.costCount = costCount;

        PlayZone stockZone = owner.getMaster().getPlayArea().getPlayZone(Zones.ZONE_STOCK);
        PlayZone waitingRoom = owner.getMaster().getPlayArea().getPlayZone(Zones.ZONE_WAITING_ROOM);
        costConditional = () -> stockZone.size() >= costCount;
        //todo Replaceable Action - can be exchanged for an effect of paying something else than stock
        payCost = () -> {
            //if cost doesnt exist, we dont do anything
            if (costCount <= 0) return;

            for (int i = 0; i < costCount; i++) {
                PlayingCard topCard = stockZone.getContents().get(Commands.Utilities.getTopOfZoneIndex(stockZone));
                Commands.moveCard(topCard, stockZone, waitingRoom, Commands.Utilities.getTopOfZoneIndex(waitingRoom),
                        CardOrientation.STAND, waitingRoom.getVisibility(), TriggerCause.GAME_ACTION, owner);
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
}

package ton.klay.wspro.core.game.formats.standard.cards.triggericons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.abilities.Ability;
import ton.klay.wspro.core.game.effects.SoulChangeContinuousEffect;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

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

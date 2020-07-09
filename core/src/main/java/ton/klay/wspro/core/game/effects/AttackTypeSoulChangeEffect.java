package ton.klay.wspro.core.game.effects;

import ton.klay.wspro.core.api.cards.abilities.Ability;
import ton.klay.wspro.core.api.game.GameRuntimeException;
import ton.klay.wspro.core.game.actions.AttackType;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

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
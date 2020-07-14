package to.klay.wspro.core.api.cards;


public interface Cost {
    void setCostConditional(CostConditional conditional);
    boolean isPayable();
    void setCostAction(PayCost payCost);
    void payCost();
}

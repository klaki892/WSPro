package ton.klay.wspro.core.api.cards;


public interface Cost {
    boolean costConditional();
    void payCost();
    //TODO verify with CostImpl.txt
}

package ton.klay.wspro.core.game.formats.standard;

/**
 * Indicates that the object is subject to fundamental ordering for the purposes of resolving continuous effects.
 */
public interface FundamentalOrderable {

    void setFundamentalOrder(int orderNumber);

    int getFundamentalOrder();
}

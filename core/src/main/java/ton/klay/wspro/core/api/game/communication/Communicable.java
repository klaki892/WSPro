package ton.klay.wspro.core.api.game.communication;

/**
 * The Communicable Interface guarantees that objects that implement it are able to have their state saved as a string. <br/>
 * This is particularly important for communications over a wire (e.g. telling a player something) <br/>
 * Objects that implement this should guarantee that the state or information contained within their object can be converted to a string. <br/>
 * <br/>
 * This can optionally be extended to be parsable if an object wishes to be
 */
public interface Communicable {
    /**
     * Creates a string representation of this object. <br/>
     * This does not guarantee that object being serialized can be converted back into the respective object. <br/>
     * This could be considered synonymous to {@link #toString()}
     * @return a string representation about the data of this object
     */
    String toCommunicableString();
}

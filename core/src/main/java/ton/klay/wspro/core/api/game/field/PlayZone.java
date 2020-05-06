package ton.klay.wspro.core.api.game.field;


import ton.klay.wspro.core.api.game.cards.GameCard;
import ton.klay.wspro.core.api.game.player.GamePlayer;

import java.util.List;

public interface PlayZone {
    /**
     * Adds a single card to the zone
     * @param card - the card to be added into the zone
     */
    void add(GameCard card);

    /**
     * Removes the specific card from the zone
     * @param card - card inside the zone to be removed
     * @return - if the card removal was successful.
     */
    boolean remove(GameCard card);

    /**
     * Clears the zone of all of the cards inside of it.
     */
    void clear();

    /**
     * Checks if a card exists inside the zone
     * @param card - the card to check for existance in the zone.
     * @return Whether the card exists in the zone
     */
    boolean contains(GameCard card);

    /**
     *  gets the name of the zone as a zone type enum
     * @return an enum of the the zone name
     * @see Zones
     */
    String getZoneName();

    /**
     * Returns if the zone is a hidden type zone
     * @return true ift he zone is a hidden zone. false if a public zone.
     */
    boolean isHiddenZone();

    /**
     * Returns the owner of this zone
     * @return The player that controls this zone.
     */
    GamePlayer getOwner();

    /**
     * Returns the number of cards inside the zone
     * @return - the number of cards currently in the zone
     */
    int size();

    /**
     * Returns whether the number of the cards in the zone is equal to 0
     * @return true if the zone doesnt have any cards inside of it.
     */
    boolean isEmpty();

    /**
     * Returns an array of all the cards that were in the zone in the order they were stored.
     * @return an array containing all of the cards in the zone
     */
    List<GameCard> getContents();

    /**
     *  Exchanges a card in the card list with a new card that isn't inside the list.
     * @param existingCard a card inside of the playzone
     * @param newCard a card outside of the playzone to be swapped in
     * @return returns true if the replacement was successful. false if not.
     */
    boolean replace(GameCard existingCard, GameCard newCard);

    /**
     * Returns the next available card, should not remove it from the zone.
     * @return
     */
    GameCard getCard();

    /**
     * Returns a card inside the zone based on if the cardID was present inside the zone. should not remove it from the zone.
     * @param cardID the id of the card to obtain
     * @return the instance of the card that contains the ID. If it cannot be found this method should return null
     */
    GameCard getCard(String cardID);
}

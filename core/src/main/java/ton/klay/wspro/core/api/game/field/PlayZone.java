package ton.klay.wspro.core.api.game.field;


import ton.klay.wspro.core.api.cards.GameVisibility;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

import java.util.List;

public interface PlayZone extends GameEntity {
    /**
     * Adds a single card to the zone
     * @param card - the card to be added into the zone
     */
    void add(PlayingCard card);

    void add(PlayingCard card, int index);

    /**
     * Removes the specific card from the zone
     * @param card - card inside the zone to be removed
     */
    void remove(PlayingCard card);

    /**
     * Checks if a card exists inside the zone
     * @param card - the card to check for existance in the zone.
     * @return Whether the card exists in the zone
     */
    boolean contains(PlayingCard card);


    GameVisibility getVisibility();

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
     * Returns an array of all the cards that were in the zone in the order they were stored.
     * @return an array containing all of the cards in the zone
     */
    List<PlayingCard> getContents();

    Zones getZoneName();
}

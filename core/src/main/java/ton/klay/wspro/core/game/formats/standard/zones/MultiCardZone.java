package ton.klay.wspro.core.game.formats.standard.zones;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.GameVisibility;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

import java.util.ArrayList;
import java.util.List;

/**
 * A zone that could be represented in a list format for functionality.
 */
public class MultiCardZone extends BasePlayZone {

    private static final Logger log = LogManager.getLogger();

    ArrayList<PlayingCard> cardList = new ArrayList<>();

    /**
     * Creates a zone with a list based implementation for holding the cards within it.
     * @param owner - The player that will own this zone.
     * @param zoneName - the name of the zone for referencing and searching
     * @param visibility  - indicates who is allowed to see the information within the zone.
     */
    public MultiCardZone(GamePlayer owner, Zones zoneName, GameVisibility visibility){
        super(owner, zoneName, visibility);
    }

    protected ArrayList<PlayingCard> getCardList() {
        return cardList;
    }

    @Override
    public void add(PlayingCard card) {
        getCardList().add(card);
    }

    @Override
    public void add(PlayingCard card, int index) {
        getCardList().add(index, card);
    }

    /**
     * Adds multiple cards to the card List<br>
     * @param cards - array of cards to be added to the zone (order is not significant)
     */
    public void add(PlayingCard[] cards) {
        for (PlayingCard card : cards)
            this.add(card);

    }


    @Override
    public void remove(PlayingCard card) {
         getCardList().remove(card);
    }

    @Override
    public boolean contains(PlayingCard card) {
        return getCardList().contains(card);
    }

    @Override
    public int size() {
        return getCardList().size();
    }

    @Override
    public List<PlayingCard> getContents() {
        return new ArrayList<>(getCardList());
    }

}

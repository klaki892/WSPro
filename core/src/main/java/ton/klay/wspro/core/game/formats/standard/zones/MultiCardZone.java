package ton.klay.wspro.core.game.formats.standard.zones;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.cards.GameCard;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.field.Zones;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A zone that could be represented in a list format for functionality.
 */
public class MultiCardZone extends BasePlayZone {

    private static final Logger log = LogManager.getLogger();

    ArrayList<GameCard> cardList = new ArrayList<>();

    /**
     * Creates a zone with a list based implementation for holding the cards within it.
     * @param owner - The player that will own this zone.
     * @param zoneName - the name of the zone for referencing and searching
     * @param isHiddenZone  - whether this zone will be treated as a hidden zone for game rules.
     */
    public MultiCardZone(GamePlayer owner, Zones zoneName, boolean isHiddenZone){
        super(owner, zoneName, isHiddenZone);
    }

    protected ArrayList<GameCard> getCardList() {
        return cardList;
    }

    @Override
    public void add(GameCard card) {
        getCardList().add(card);
        card.setPosition(this);
    }

    /**
     * Adds multiple cards to the card List<br>
     * @param cards - array of cards to be added to the zone (order is not significant)
     */
    public void add(GameCard[] cards) {
        for (GameCard card : cards)
            this.add(card);

    }


    @Override
    public boolean remove(GameCard card) {
        return getCardList().remove(card);
    }

    /**
     * Removes the specified cards from the zone <br/>
     * If multiple of the same card exists, it will only remove the instance that appears first for each
     * @param cards - an array of cards to be removed
     * @return - Whether all cards were removed or not
     */
    public boolean remove(GameCard[] cards) {
        for(GameCard card : cards) {
            if (!this.remove(card))
                return false;
        }

        return false; //reutrn false if the array was already empty
    }


    @Override
    public void clear() {
        getCardList().clear();
    }

    @Override
    public boolean contains(GameCard card) {
        return getCardList().contains(card);
    }

    @Override
    public int size() {
        return getCardList().size();
    }

    @Override
    public boolean isEmpty() {
        return getCardList().isEmpty();
    }

    @Override
    public List<GameCard> getContents() {
        return getCardList();
    }

    /**
     * Sets the card list to the given list of cards in the order of which they appear <br/>
     */
    public void setContents(GameCard[] cardList){
        this.clear();
        getCardList().addAll(Arrays.asList(cardList));
    }

    @Override
    public boolean replace(GameCard existingCard, GameCard newCard) {
        int existingIndex = getCardList().indexOf(existingCard);

        if (existingIndex != -1){
            getCardList().add(existingIndex, newCard);
            getCardList().remove(existingCard);

            //replace the cardlist with the new change
            setContents(getCardList().toArray(new GameCard[this.size()]));

            return true;
        }
        return false;

    }

    @Override
    public GameCard getCard() {
        return cardList.get(0);
    }

    @Override
    public GameCard getCard(String cardID) {
        for (GameCard card : cardList){
            if (card.getID().equals(cardID))
                return card;
        }
        return null;
    }


}

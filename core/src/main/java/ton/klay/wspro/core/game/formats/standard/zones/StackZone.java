package ton.klay.wspro.core.game.formats.standard.zones;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.cards.GameCard;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.field.Zones;

import java.util.*;

/**
 * a zone that could be represented in a stack(First-In-Last-Out) functionality(e.g. a stack of cards if IRL)
 */
public class StackZone extends BasePlayZone {

    private static final Logger log = LogManager.getLogger();

    private ArrayDeque<GameCard> cardStack = new ArrayDeque<>();

    /**
     * Creates a zone with a stack based implementation for holding the cards within it.
     * @param owner - The player that will own this zone.
     * @param zoneName - the name of the zone for referencing and searching
     * @param isHiddenZone  - whether this zone will be treated as a hidden zone for game rules.
     */
    public StackZone(GamePlayer owner, Zones zoneName, boolean isHiddenZone){
        super(owner, zoneName, isHiddenZone);
    }


    /**
     * Adds a card to the bottom of the stack of cards
     * @param card - card to be added to the stack
     */
    public void addBottom(GameCard card){
        cardStack.addFirst(card);
    }


    /**
     * Adds a card to the top of the stack
     * @param card - the card to be added into the zone
     */
    @Override
    public void add(GameCard card) {
        cardStack.add(card);
        card.setPosition(this);
    }

    /**
     * Adds multiple cards to the top of the stack zone in the order in which they appear.<br>
     * Note, the first index in the card List will be at the bottom of the card Stack
     * e.g. the first card in the array will be the last to come out if all were popped.
     * @param cards - array of cards to be added to the zone (order may or may not be significant)
     */
    public void add(GameCard[] cards) {
        for (GameCard card : cards)
            this.add(card);

    }

    /**
     * Removes a card from the card stack. <br>
     * Note: if multiple of the same card exists, it will only remove the instance that appears first.
     * @param card - card inside the zone to be removed
     * @return - Whether the card removal was successful
     */
    @Override
    public boolean remove(GameCard card) {
        return cardStack.removeLastOccurrence(card);
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

    /**
     * REmoves a card from the bottom of the stack of cards
     * @return Whether the card was removed or not
     */
    public boolean removeBottom(){
        try {
            this.popBottom();
            return true;
        } catch (NoSuchElementException ex){
            return false;
        }
    }

    /**
     * Removes a card from the top of the card stack and returns it
     * @return the card removed from the top of the card stack.
     */
    public GameCard pop(){
        return cardStack.pop();
    }

    /**
     * Removes as many cards from the card stack it can and returns a list containing them.<br/>
     * If there arent enough to reach the specified number, the function will return what cards it did pop
     * @param numberOfCards - number of cards to be popped off the stack
     * @return - the list of cards that were removed from the card stack
     */
    public GameCard[] popMultiple(int numberOfCards){

        GameCard[] returnList = null;

        //check if we dont have enough cards, if we dont have enough, only show as many as we can.
        if (numberOfCards > this.size()){
            returnList = this.getContents().toArray(new GameCard[numberOfCards]);
            this.clear();
        }
        else{

            returnList = new GameCard[numberOfCards];
            for (GameCard card : returnList)
                card = this.pop();
        }

        return returnList;

    }

    /**
     * Removes a card from the bottom fo the card stack adn returns it
     * @return the card removed from the bottom of the card stack
     */
    public GameCard popBottom(){
        return cardStack.removeFirst();
    }

    /**
     * Look at, but dont remove the top card from the card stack
     * @return the card at the top of the card stack.
     */
    public GameCard peek(){
        return cardStack.peek();
    }

    /**
     * Look at, but dont remove a certain number of elements from the card list. <br/>
     * if there arent enough for the specified amount, it will return a list of as many as remained in the card list.
     * @param numberOfCards the number of cards to look at from the top of the card list.
     * @return a list of cards in the order of which they were seen
     */
    public GameCard[] peekMultiple(int numberOfCards){

        //check if we dont have enough cards, if we dont have enough, only show as many as we can.
        if (numberOfCards > this.size()){
            numberOfCards = this.size();
        }

        GameCard[] returnList = new GameCard[numberOfCards];
        GameCard[] cardList = getContents().toArray(new GameCard[numberOfCards]);
        for (int i = 0; i < numberOfCards; i++){
            returnList[i] = cardList[i];
        }

        return returnList;

    }


    @Override
    public void clear() {
        cardStack.clear();
    }

    @Override
    public boolean contains(GameCard card) {
        return cardStack.contains(card);
    }

    /**
     * Checks if a certain set of cards exists inside the zone
     * @param cards - an array containing the cards to check for
     * @return - Whether the cards exist in the zone
     */
    public boolean contains(GameCard[] cards) {

        for (GameCard card : cards)
            if (!cardStack.contains(card))
                return false;

        return true;
    }

    @Override
    public int size() {
        return cardStack.size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public List<GameCard> getContents() {
        return new ArrayList<>(cardStack);
    }

    /**
     * Sets the cardStack to the given list of cards in the order of which they appear <br/>
     * Note, the first index in the cardList will be at the bottom of the card Stack <br/>
     * e.g. the first card in the array will be the last to come out if all were popped.
     */
    public void setContents(GameCard[] cardList){
        this.clear();
        this.add(cardList);
    }

    @Override
    public boolean replace(GameCard existingCard, GameCard newCard){

        ArrayList<GameCard> cardList = new ArrayList<>(getContents());

        int existingIndex = cardList.indexOf(existingCard);

        if (existingIndex != -1){
            cardList.add(existingIndex, newCard);
            cardList.remove(existingCard);

            //replace the cardlist with the new change
            setContents(cardList.toArray(new GameCard[cardList.size()]));

            return true;
        }
        return false;
    }

    @Override
    public GameCard getCard() {
        return cardStack.peekFirst();
    }

    @Override
    public GameCard getCard(String cardID) {
        for (GameCard card : cardStack) {
            if (card.getID().equals(cardID))
                return card;
        }
        return null;
    }
}

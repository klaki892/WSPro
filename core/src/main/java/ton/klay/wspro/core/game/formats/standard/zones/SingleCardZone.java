package ton.klay.wspro.core.game.formats.standard.zones;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.cards.GameCard;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.field.Zones;

import java.util.ArrayList;
import java.util.List;

/**
 * A zone that can be represented as though it will only have 1 card at all points in time
 */
public class SingleCardZone extends BasePlayZone {

    private static final Logger log = LogManager.getLogger();

    protected GameCard lastCard = null;
    private GameCard card = null;

    /**
     * Creates a zone with a Single-Card implementation. Holds 1 card at any given time but does keep reference the last card that was in it.
     * @param owner - The player that will own this zone.
     * @param zoneName - the name of the zone for referencing and searching
     * @param isHiddenZone  - whether this zone will be treated as a hidden zone for game rules.
     */
    public SingleCardZone(GamePlayer owner, Zones zoneName, boolean isHiddenZone){
        super(owner, zoneName, isHiddenZone);
    }

    protected void setCard(GameCard card) {
        setLastCard(getCard());
        this.card = card;
    }

    protected void setLastCard(GameCard lastCard) {
        this.lastCard = lastCard;
    }

    @Override
    public void add(GameCard card) {
        setCard(card);

    }

    @Override
    public boolean remove(GameCard card) {
        if (this.contains(card)){
            this.clear();
            return this.isEmpty();
        }
        else
            return false;
    }

    /**
     * Synonymous with {@link #clear()}
     * @return if the removal of the card was successful
     * @see  #clear()
     */
    public boolean remove(){
        this.clear();
        return this.isEmpty();
    }

    @Override
    public void clear() {
        setCard(null);
    }

    @Override
    public boolean contains(GameCard card) {
        return getCard().equals(card);
        //FIXME note: we did a Java.equals card comparison here. keep note if needs to be changed in future
    }

    @Override
    public int size() {
        return this.isEmpty() ? 0 : 1;
    }

    @Override
    public boolean isEmpty() {
        return getCard() == null;
    }

    @Override
    public List<GameCard> getContents() {
        List<GameCard> list = new ArrayList<>();
        list.add(getCard());
        return list;
    }

    /**
     * Returns the card that is in the zone at the current moment in time.
     * @return A reference to the card in the current zone. returns null if empty.
     */
    public GameCard getCard() {
        return card;
    }

    @Override
    public GameCard getCard(String cardID) {
        return card.getID().equals(cardID) ? card : null;
    }

    /**
     * Returns the last card that existed in the zone before the current state of the zone.
     * @return The last card that was present in the zone. Null if no previous card before the current state.
     */
    public GameCard getLastCard() {
        return lastCard;
    }

    @Override
    public boolean replace(GameCard existingCard, GameCard newCard) {
        if (this.contains(existingCard)){
            this.add(newCard);
            return this.contains(newCard);
        }
        else
            return false;
    }
}

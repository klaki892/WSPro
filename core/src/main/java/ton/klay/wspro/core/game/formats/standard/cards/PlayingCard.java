package ton.klay.wspro.core.game.formats.standard.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.*;
import ton.klay.wspro.core.api.cards.abilities.TriggerableAbility;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.setup.GameLocale;
import ton.klay.wspro.core.api.scripting.cards.CardType;
import ton.klay.wspro.core.game.Game;
import ton.klay.wspro.core.game.formats.standard.FundamentalOrderable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlayingCard implements GameEntity, FundamentalOrderable {

    private static final Logger log = LogManager.getLogger();
    private final Game game;
    private final PaperCard baseCard;
    private String guid;

    private final  List<TriggerableAbility> triggerableAbilities = new ArrayList<>();
    private CardOrientation orientation;
    private final GamePlayer owner;
    private GamePlayer master;
    private GameVisibility visibility;
    private final List<PlayingCard> markers = new ArrayList<>();

    private boolean canStand = true;
    private boolean canRest = true;
    private boolean canBeReversed = true;
    private boolean canBeTargeted = true;

    private int fundamentalOrder;

    private Collection<LocalizedString> cardName;
    private int level;
    private Cost cost;
    private CardIcon icon;
    private int power;
    private int soul;
    private CardColor color;
    private CardType cardType;
    private Collection<LocalizedString> titleName;
    private String id;
    private CardAffiliation affiliations;



    public PlayingCard(Game game, PaperCard card, GamePlayer master, GamePlayer owner){
        this.game = game;
        this.baseCard = card;
        this.owner = owner;
        this.master = master;
        this.fundamentalOrder = game.getNextFundamentalOrder();

        copyBaseStats(baseCard);


        //generate the uniqueID
        refreshGUID();

        //todo populate abilities

        //register abilities in the game so they start recieving events
        for (TriggerableAbility ability : triggerableAbilities){
            ability.register(game.getTriggerManager());
        }

    }

    private void copyBaseStats(PaperCard c) {
        setCardName(c.getCardName());
        setLevel(c.getLevel());
        setCost(c.getCost());
        setIcon(c.getIcon());
        setPower(c.getPower());
        setSoul(c.getSoul());
        setColor(c.getColor());
        setCardType(c.getCardType());
        setTitleName(c.getTitleName());
        setId(c.getID());
        setAffiliations(c.getAffiliations());
    }

    /**
         * Playing Cards are deregistered when they are no longer going to be used in the game (ex: removed becuase card is moving zones) <br/>
         * Deregistering unregisters all ability trigger listeners. And makes the Playing card Immutable
         */
    public void deregister() {
        //todo set last known information, make all getters immutable from this point on,
        for (TriggerableAbility ability : triggerableAbilities){
            game.getTriggerManager().unregister(ability);
        }
    }

    public void refreshGUID(){
        boolean updating = (guid != null);
        guid = game.getRandomGuid();
        if (updating) {
            log.trace("Updating " + guid + " to " + baseCard.getID());
        } else {
            log.trace("Assigned " + guid + " to " + baseCard.getID());
        }
    }

    public void reset() {
        copyBaseStats(baseCard);

    }

    @Override
    public String toString() {
        return  baseCard.getID() + " || "+ baseCard.getCardName();
    }

    /**
         * Get the GUID (Game - Unique - ID) for this particular GameCard.
         * @return a GUID in string form
         */
    public String getGUID() {
        return guid;
    }

    /**
         * The {@link PaperCard} which this card is based off of
         * @return the Papercard used to create this playing card
         */
    public PaperCard getPaperCard() {
        return baseCard;
    }

    /**
         * get the orientation of a card. For the standard game, orientation should only come into play if the card is on the stage.
         * @return orientation the orientation in which the card should be placed.
         * @see CardOrientation
         */
    public CardOrientation getOrientation() {
        return orientation;
    }

    /**
         * Set the orientation of a card. For the standard game, orientation should only come into play if the card is on the stage.
         * @param orientation the orientation in which the card should be placed.
         * @see CardOrientation
         */
    public void setOrientation(CardOrientation orientation) {
        this.orientation = orientation;
    }

    /**
         * get the master of this card. This could change as the game progresses
         * @see <code>Weiss Schwarz rule 4.3</code>
         * @return the id of the master of this card
         */
    public GamePlayer getMaster() {
        return master;
    }

    /**
         * Get the owner of this card. aka the person who brought this card into the game
         * @see <code>Weiss Scharz rule 4.2</code>
         * @return the instance which owns this ccard
         */
    public GamePlayer getOwner() {
        return owner;
    }

    public GameVisibility getVisibility() {
        return visibility;
    }

    public Collection<PlayingCard> getMarkers() {
        return markers;
    }

    public boolean canStand() {
        return canStand;
    }

    public boolean canRest() {
        return canRest;
    }

    public boolean canBeReversed() {
        return canBeReversed;
    }

    public boolean canBeTargeted() {
        return canBeTargeted;
    }

    /**
     * Gets a specific card name based on the Locale
     *
     * @return the name of a card in a specific locale.
     * @see GameLocale
     * @see <code>Weiss Schwarz rule 2.1</code>
     */
    public Collection<LocalizedString> getCardName() {
        return cardName;
    }

    public void setMaster(GamePlayer master) {
        this.master = master;
    }

    public void setVisibility(GameVisibility visibility) {
        this.visibility = visibility;
    }

    public void setCanStand(boolean canStand) {
        this.canStand = canStand;
    }

    public void setCanRest(boolean canRest) {
        this.canRest = canRest;
    }

    public void setCanBeReversed(boolean canBeReversed) {
        this.canBeReversed = canBeReversed;
    }

    public void setCanBeTargeted(boolean canBeTargeted) {
        this.canBeTargeted = canBeTargeted;
    }

    public void setCardName(Collection<LocalizedString> cardName) {
        this.cardName = cardName;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    public void setIcon(CardIcon icon) {
        this.icon = icon;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public void setSoul(int soul) {
        this.soul = soul;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public void setTitleName(Collection<LocalizedString> titleName) {
        this.titleName = titleName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAffiliations(CardAffiliation affiliations) {
        this.affiliations = affiliations;
    }

    public String getGuid() {
        return guid;
    }

    public List<TriggerableAbility> getTriggerableAbilities() {
        return triggerableAbilities;
    }

    public boolean isCanStand() {
        return canStand;
    }

    public boolean isCanRest() {
        return canRest;
    }

    public boolean isCanBeReversed() {
        return canBeReversed;
    }

    public boolean isCanBeTargeted() {
        return canBeTargeted;
    }

    public int getLevel() {
        return level;
    }

    public Cost getCost() {
        return cost;
    }

    public CardIcon getIcon() {
        return icon;
    }

    public int getPower() {
        return power;
    }

    public int getSoul() {
        return soul;
    }

    public CardColor getColor() {
        return color;
    }

    public CardType getCardType() {
        return cardType;
    }

    public Collection<LocalizedString> getTitleName() {
        return titleName;
    }
    /**
         * Returns the CollectionID contained within this GameCard
         * @return the collectionID of the card
         */
    public String getID() {
        return id;
    }

    public CardAffiliation getAffiliations() {
        return affiliations;
    }

    @Override
    public void setFundamentalOrder(int orderNumber) {
        fundamentalOrder = orderNumber;
    }

    @Override
    public int getFundamentalOrder() {
        return fundamentalOrder;
    }

    public Game getGame() {
        return game;
    }
}

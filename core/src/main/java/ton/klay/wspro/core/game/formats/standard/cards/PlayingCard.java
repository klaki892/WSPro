package ton.klay.wspro.core.game.formats.standard.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.CardAffiliation;
import ton.klay.wspro.core.api.cards.CardColor;
import ton.klay.wspro.core.api.cards.CardIcon;
import ton.klay.wspro.core.api.cards.CardOrientation;
import ton.klay.wspro.core.api.cards.CardTrigger;
import ton.klay.wspro.core.api.cards.Cost;
import ton.klay.wspro.core.api.cards.GameVisibility;
import ton.klay.wspro.core.api.cards.LocalizedString;
import ton.klay.wspro.core.api.cards.PaperCard;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.setup.GameLocale;
import ton.klay.wspro.core.game.Game;
import ton.klay.wspro.core.game.cardLogic.ability.ActivatedAbility;
import ton.klay.wspro.core.game.cardLogic.ability.TypedAbilityList;
import ton.klay.wspro.core.game.cards.CardType;
import ton.klay.wspro.core.game.formats.standard.FundamentalOrderable;
import ton.klay.wspro.core.game.formats.standard.triggers.listeners.TriggerableAbilityListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PlayingCard implements GameEntity, FundamentalOrderable {

    private static final Logger log = LogManager.getLogger();
    protected final transient Game game;
    protected final transient PaperCard baseCard;
    protected String guid;

    protected final transient List<TriggerableAbilityListener> triggerableAbilities = new ArrayList<>();
    protected final transient List<ActivatedAbility> activatedAbilities = new ArrayList<>();
    protected CardOrientation orientation;
    protected final transient GamePlayer owner;
    protected transient GamePlayer master;
    protected GameVisibility visibility;
    protected final List<PlayingCard> markers = new ArrayList<>();

    protected boolean canStand = true;
    protected boolean canRest = true;
    protected boolean canBeReversed = true;
    protected boolean canBeTargeted = true;
    protected boolean canFrontalAttack = true;
    protected boolean canSideAttack = true;
    protected boolean canDirectAttack = true;


    protected int fundamentalOrder;

    protected Collection<LocalizedString> cardName;
    protected int level;
    protected transient Cost costActions;
    protected int cost;
    protected CardIcon icon;
    protected int power;
    protected int soul;
    protected CardColor color;
    protected CardType cardType;
    protected Collection<LocalizedString> titleName;
    protected String id;
    protected CardAffiliation affiliations;
    protected Collection<LocalizedString> traits;
    protected Collection<CardTrigger> triggerIcons;


    public PlayingCard(Game game, PaperCard card, GamePlayer master, GamePlayer owner){
        this.game = game;
        this.baseCard = card;
        this.owner = owner;
        this.master = master;
        this.fundamentalOrder = game.getNextFundamentalOrder();

        copyBaseStats(baseCard);


        //generate the uniqueID
        refreshGUID();

        TypedAbilityList abilities = game.getAbilityFinder().getAbilitiesForCard(game, this);
        triggerableAbilities.addAll(abilities.getTriggerableAbilityListeners());
        activatedAbilities.addAll(abilities.getActivateAbilities());


        //todo remove this, all abilities should be sourced from external source
//        if (baseCard.getCardType() == CardType.CHARACTER)
//            triggerableAbilities.add(new DefaultEncoreAbilityListener(this));

        //register abilities in the game so they start recieving events
        for (TriggerableAbilityListener ability : triggerableAbilities){
            ability.register(game.getTriggerManager());
        }

    }

    private void copyBaseStats(PaperCard c) {
        setCardName(c.getCardName());
        setLevel(c.getLevel());
        setCost(c.getCost());
        setCostActions(new StockCost.Builder().setOwner(this).setCostCount(c.getCost()).createStockCost());
        setIcon(c.getIcon());
        setPower(c.getPower());
        setSoul(c.getSoul());
        setColor(c.getColor());
        setCardType(c.getCardType());
        setTitleName(c.getTitleName());
        setId(c.getID());
        setAffiliations(c.getAffiliations());
        setTraits(c.getTraits());
        setTriggerIcons(c.getTriggerIcons());
    }


    /**
         * Playing Cards are deregistered when they are no longer going to be used in the game (ex: removed becuase card is moving zones) <br/>
         * Deregistering unregisters all ability trigger listeners. And makes the Playing card Immutable
         */
    public void deregister() {
        //todo set last known information, make all getters immutable from this point on,
        for (TriggerableAbilityListener ability : triggerableAbilities){
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
        return new ArrayList<>(markers);
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

    public boolean canFrontalAttack() {
        return canFrontalAttack;
    }

    public void setCanFrontalAttack(boolean canFrontalAttack) {
        this.canFrontalAttack = canFrontalAttack;
    }

    public boolean canSideAttack() {
        return canSideAttack;
    }

    public void setCanSideAttack(boolean canSideAttack) {
        this.canSideAttack = canSideAttack;
    }

    public boolean canDirectAttack() {
        return canDirectAttack;
    }

    public void setCanDirectAttack(boolean canDirectAttack) {
        this.canDirectAttack = canDirectAttack;
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

    public void setCostActions(Cost costActions) {
        this.costActions = costActions;
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

    public List<TriggerableAbilityListener> getTriggerableAbilities() {
        return triggerableAbilities;
    }
    public void addTriggerableAbility(TriggerableAbilityListener triggerableAbilityListener){
        triggerableAbilities.add(triggerableAbilityListener);
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public int getLevel() {
        return level;
    }

    public Cost getCostActions() {
        return costActions;
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

    public Collection<LocalizedString> getTraits() {
        return traits;
    }
    public void setTraits(Collection<LocalizedString> traits) {

        this.traits = traits;
    }

    public Collection<CardTrigger> getTriggerIcons() {
        return triggerIcons;
    }

    public void setTriggerIcons(Collection<CardTrigger> triggerIcons) {
        this.triggerIcons = triggerIcons;
    }

    public List<ActivatedAbility> getActivatableAbilities() {
        return activatedAbilities;
    }
}

package ton.klay.wspro.core.game.formats.standard.cards;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.proto.ProtoPlayingCard;
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
import ton.klay.wspro.core.game.proto.CardAffiliationProtoTypeConverter;
import ton.klay.wspro.core.game.proto.CardColorTypeConverter;
import ton.klay.wspro.core.game.proto.CardIconTypeConverter;
import ton.klay.wspro.core.game.proto.CardOrientationTypeConverter;
import ton.klay.wspro.core.game.proto.CardTriggerCollectionProtoTypeConverter;
import ton.klay.wspro.core.game.proto.CardTypeProtoTypeConverter;
import ton.klay.wspro.core.game.proto.GameVisibilityTypeConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@ProtoClass(ProtoPlayingCard.class)
public class PlayingCard implements GameEntity, FundamentalOrderable {

    private static final Logger log = LogManager.getLogger();
    protected final transient Game game;
    protected final transient PaperCard baseCard;

    protected final transient List<TriggerableAbilityListener> triggerableAbilities = new ArrayList<>();
    protected final transient List<ActivatedAbility> activatedAbilities = new ArrayList<>();
    protected final transient GamePlayer owner;

    @ProtoField
    protected  GamePlayer master;
    @ProtoField
    protected String guid;

    @ProtoField(converter = CardOrientationTypeConverter.class)
    protected CardOrientation orientation;

    @ProtoField(converter = GameVisibilityTypeConverter.class)
    protected GameVisibility visibility;

    @ProtoField
    protected final List<PlayingCard> markers = new ArrayList<>();

    @ProtoField
    protected boolean standable = true;
    @ProtoField
    protected boolean restable = true;
    @ProtoField
    protected boolean reversable = true;
    @ProtoField
    protected boolean targetable = true;
    @ProtoField
    protected boolean frontalAttackCapable = true;
    @ProtoField
    protected boolean sideAttackCapable = true;
    @ProtoField
    protected boolean directAttackCapable = true;

    @ProtoField
    protected int fundamentalOrder;

    @ProtoField
    protected Collection<LocalizedString> cardName;
    @ProtoField
    protected int level;

    protected transient Cost costActions;

    @ProtoField
    protected int cost;

    @ProtoField(converter = CardIconTypeConverter.class)
    protected CardIcon icon;
    @ProtoField
    protected int power;
    @ProtoField
    protected int soul;
    @ProtoField(converter = CardColorTypeConverter.class)
    protected CardColor color;
    @ProtoField(converter = CardTypeProtoTypeConverter.class)
    protected CardType cardType;
    @ProtoField
    protected Collection<LocalizedString> titleName;
    @ProtoField
    protected String id;
    @ProtoField(converter = CardAffiliationProtoTypeConverter.class)
    protected CardAffiliation affiliations;
    @ProtoField
    protected Collection<LocalizedString> traits;
    @ProtoField(converter = CardTriggerCollectionProtoTypeConverter.class)
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
    public String getGuid() {
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

    public boolean isStandable() {
        return standable;
    }

    public boolean isRestable() {
        return restable;
    }

    public boolean isReversable() {
        return reversable;
    }

    public boolean isTargetable() {
        return targetable;
    }

    public boolean isFrontalAttackCapable() {
        return frontalAttackCapable;
    }

    public void setFrontalAttackCapable(boolean frontalAttackCapable) {
        this.frontalAttackCapable = frontalAttackCapable;
    }

    public boolean isSideAttackCapable() {
        return sideAttackCapable;
    }

    public void setSideAttackCapable(boolean sideAttackCapable) {
        this.sideAttackCapable = sideAttackCapable;
    }

    public boolean isDirectAttackCapable() {
        return directAttackCapable;
    }

    public void setDirectAttackCapable(boolean directAttackCapable) {
        this.directAttackCapable = directAttackCapable;
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

    public void setStandable(boolean standable) {
        this.standable = standable;
    }

    public void setRestable(boolean restable) {
        this.restable = restable;
    }

    public void setReversable(boolean reversable) {
        this.reversable = reversable;
    }

    public void setTargetable(boolean targetable) {
        this.targetable = targetable;
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
    public String getId() {
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

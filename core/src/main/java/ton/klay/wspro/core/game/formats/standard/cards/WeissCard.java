package ton.klay.wspro.core.game.formats.standard.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.abilities.components.effects.EffectModification;
import ton.klay.wspro.core.api.game.cards.CardAttribute;
import ton.klay.wspro.core.api.game.cards.GameCard;
import ton.klay.wspro.core.api.cards.abilities.Ability;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.api.cards.Card;
import ton.klay.wspro.core.api.game.setup.GameLocale;
import ton.klay.wspro.core.game.formats.standard.abilities.effects.ContinuousEffectComparator;

import java.util.*;

public class WeissCard implements GameCard, Card {

    private static final int NAME_COMPLEXITY = 4;
    private static final Logger log = LogManager.getLogger();
    private static final int INITIAL_EFFECTS_EXPECTED = 3;
    private final GameContext context;
    private final Card basecard;
    private final String guid;

    //gamestate specific variables
    private String ownerID;
    private String master;
    private String orientation;
    private PlayZone curStageZone;

    //effect modification lists
    private ContinuousEffectComparator<Integer> intComparator = new ContinuousEffectComparator<>();
    private ContinuousEffectComparator<String> stringComparator = new ContinuousEffectComparator<>();
    private ContinuousEffectComparator<Collection<String>> stringListComparator = new ContinuousEffectComparator<>();

    private PriorityQueue<EffectModification<String>> nameEffectQueue = new PriorityQueue<>(INITIAL_EFFECTS_EXPECTED, stringComparator);
    private PriorityQueue<EffectModification<Integer>> levelEffectQueue = new PriorityQueue<>(INITIAL_EFFECTS_EXPECTED, intComparator);
    private PriorityQueue<EffectModification<Integer>> costEffectQueue = new PriorityQueue<>(INITIAL_EFFECTS_EXPECTED, intComparator);
    private PriorityQueue<EffectModification<String>> iconEffectQueue = new PriorityQueue<>(INITIAL_EFFECTS_EXPECTED, stringComparator);
    private PriorityQueue<EffectModification<Collection<String>>> triggerIconsEffectQueue = new PriorityQueue<>(INITIAL_EFFECTS_EXPECTED, stringListComparator);
    private PriorityQueue<EffectModification<Integer>> powerEffectQueue = new PriorityQueue<>(INITIAL_EFFECTS_EXPECTED, intComparator);
    private PriorityQueue<EffectModification<Integer>> soulEffectQueue = new PriorityQueue<>(INITIAL_EFFECTS_EXPECTED, intComparator);
    private PriorityQueue<EffectModification<Collection<String>>> traitsIconsEffectQueue = new PriorityQueue<>(INITIAL_EFFECTS_EXPECTED, stringListComparator);
    private PriorityQueue<EffectModification<String>> colorEffectQueue = new PriorityQueue<>(INITIAL_EFFECTS_EXPECTED, stringComparator);
    private PriorityQueue<EffectModification<Collection<String>>> affiliationEffectQueue = new PriorityQueue<>(INITIAL_EFFECTS_EXPECTED, stringListComparator);

    private Map<CardAttribute, PriorityQueue> attributeMap = new HashMap<>(11, 1);

    public WeissCard(GameContext context, Card card, String ownerID){
        this.context = context;
        this.basecard = card;
        this.ownerID = ownerID;
        this.master = ownerID;

        //generate the uniqueID
        byte[] byteHolder = new byte[NAME_COMPLEXITY];
        context.getRandom().nextBytes(byteHolder);
        guid = UUID.nameUUIDFromBytes(byteHolder).toString();
        log.trace("Assigned " + guid + " to " + basecard.getID());

        //todo map all the PQ's to their respective named property
        mapAttributes();

        //todo tell context script manager to lookup and register our effects

    }

    @Override
    public void registerAbility(Ability ability) {
    }

    @Override
    public void RegisterEffect(String property, EffectModification effect) {
        //todo add an effectModification to an existing queue based on the property (if its not already present)

    }

    @Override
    public void setMaster(String master) {
        log.trace("Set new Master (" + master + ") to card: " + guid + " AKA " + basecard.getID());
        this.master = master;
    }

    @Override
    public void setOwner(String owner) {
        log.trace("Set new owner (" + owner + ") to card: " + guid + " AKA " + basecard.getID());
        this.ownerID = owner;
    }

    @Override
    public String getMaster() {
        return master;
    }

    @Override
    public String getOwner() {
        return ownerID;
    }

    @Override
    public String getGUID() {
        return guid;
    }

    @Override
    public void setPosition(PlayZone playZone) {
        curStageZone = playZone;
    }

    @Override
    public PlayZone getPosition() {
        return curStageZone;
    }

    @Override
    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    @Override
    public String getOrientation() {
        return orientation;
    }

    @Override
    public void reset() {

    }

                                /***********CARD IMPLEMENTATION ************/

    @Override
    public String getEnCardName() {
        //todo does this need an effect queue?
        return basecard.getEnCardName();
    }

    @Override
    public String getJpCardName() {
        //todo does this need an effect queue?
        return basecard.getEnCardName();
    }

    @Override
    public String getCardName(String locale) {
        //todo does this need an effect queue?
        return basecard.getCardName(locale);
    }

    @Override
    public int getLevel() {
        return new ValueWithEffects<>(context,
                basecard.getLevel(),
                levelEffectQueue).getModifiedValue();
    }



    @Override
    public int getCost() {
        return new ValueWithEffects<>(context,
                basecard.getCost(),
                costEffectQueue).getModifiedValue();

    }

    @Override
    public String getIcon() {
        return new ValueWithEffects<>(context,
                basecard.getIcon(),
                iconEffectQueue).getModifiedValue();
    }

    @Override
    public Collection<String> getTriggerIcons() {
        return new ValueWithEffects<>(context,
                basecard.getTriggerIcons(),
                triggerIconsEffectQueue).getModifiedValue();
    }

    @Override
    public String getCardText() {
        return basecard.getCardText();
    }

    @Override
    public String getCardText(String locale) {
        return basecard.getCardText(locale);
    }

    @Override
    public int getPower() {
        return new ValueWithEffects<>(context,
                basecard.getPower(),
                powerEffectQueue).getModifiedValue();

    }

    @Override
    public int getSoul() {
        return new ValueWithEffects<>(context,
                basecard.getSoul(),
                soulEffectQueue).getModifiedValue();

    }

    @Override
    public Collection<String> getTraits() {
        return new ValueWithEffects<>(context,
                basecard.getTraits(),
                traitsIconsEffectQueue).getModifiedValue();
    }

    @Override
    public String getColor() {
        return new ValueWithEffects<>(context,
                basecard.getColor(),
                colorEffectQueue).getModifiedValue();
    }

    @Override
    public String getCardType() {
        return basecard.getCardType();
    }

    @Override
    public String getTitleName() {
        return basecard.getTitleName();
    }

    @Override
    public String getID() {
        return basecard.getID();
    }

    @Override
    public String getRarity() {
        return basecard.getRarity();
    }

    @Override
    public Collection<String> getAffiliations() {
        return new ValueWithEffects<>(context,
                basecard.getAffiliations(),
                affiliationEffectQueue).getModifiedValue();
    }

    @Override
    public String toString() {
        return  basecard.getID() + " || "+ basecard.getCardName(GameLocale.EN.name());
    }

    private void mapAttributes() {

        attributeMap.put(CardAttribute.NAME, nameEffectQueue);
        attributeMap.put(CardAttribute.LEVEL, levelEffectQueue);
        attributeMap.put(CardAttribute.COST, costEffectQueue);
        attributeMap.put(CardAttribute.ICON, iconEffectQueue);
        attributeMap.put(CardAttribute.TRIGGER_ICONS, triggerIconsEffectQueue);
        attributeMap.put(CardAttribute.POWER, powerEffectQueue);
        attributeMap.put(CardAttribute.SOUL, soulEffectQueue);
        attributeMap.put(CardAttribute.TRAITS, traitsIconsEffectQueue);
        attributeMap.put(CardAttribute.COLOR, colorEffectQueue);
        attributeMap.put(CardAttribute.AFFILIATION, affiliationEffectQueue);
    }


    private class ValueWithEffects<T>{


        T modifiedValue;

        ValueWithEffects(GameContext context, T originalValue, PriorityQueue<EffectModification<T>> modifications){
            modifiedValue = valueWithEffectsApplied(context, originalValue, modifications);
        }

        T valueWithEffectsApplied(GameContext context, T originalValue, PriorityQueue<EffectModification<T>> effectQueue) {
            //todo implement as described in the continuous effect docs
            //todo make a copy of the PQ so that we dont get a concurrent modification exception removing items from our real queue
            return null;
        }

        T getModifiedValue() {
            return modifiedValue;
        }
    }
}

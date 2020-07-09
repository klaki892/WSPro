package ton.klay.wspro.core.game.actions;

import com.google.common.base.MoreObjects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.LocalizedString;
import ton.klay.wspro.core.api.cards.abilities.Ability;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.game.events.InterruptRuleAction;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

public class PlayChoice {

    private static final Logger log = LogManager.getLogger();
    private final PlayChoiceType choiceType;
    private InterruptRuleAction interruptRuleAction;
    private Ability ability;
    private PlayZonePair stagePositionPair;
    private AttackPositionPair attackPositionPair;
    private PlayZone zone;
    private LocalizedString string;

    private PlayingCard card;
    private PlayChoiceAction action;

    private PlayChoice(PlayChoiceType choiceType, PlayingCard card){
        this(choiceType);
        this.card = card;
    }
    private PlayChoice(PlayChoiceType choiceType, Ability ability){
        this(choiceType);
        this.ability = ability;
    }
    private PlayChoice(PlayChoiceType choiceType, PlayZonePair stagePositionPair){
        this(choiceType);
        this.stagePositionPair = stagePositionPair;
    }
    private PlayChoice(PlayChoiceType choiceType, PlayChoiceAction action){
        this(choiceType);
        this.action = action;
    }
    private PlayChoice(PlayChoiceType choiceType, AttackPositionPair attackPositionPair){
        this(choiceType);
        this.attackPositionPair = attackPositionPair;
    }
    private PlayChoice(PlayChoiceType choiceType, PlayZone zone){
        this(choiceType);
        this.zone = zone;
    }
    private PlayChoice(PlayChoiceType choiceType, LocalizedString string){
        this(choiceType);
        this.string = string;
    }
    private PlayChoice(PlayChoiceType choiceType, InterruptRuleAction interruptRuleAction){
        this(choiceType);

        this.interruptRuleAction = interruptRuleAction;
    }

    private PlayChoice(PlayChoiceType choiceType){
        this.choiceType = choiceType;
    }

    public static PlayChoice makeCardChoice(PlayingCard card) {
        return new PlayChoice(PlayChoiceType.CHOOSE_CARD, card);
    }
    public static PlayChoice makeAbilityChoice(Ability ability) {
        return new PlayChoice(PlayChoiceType.CHOOSE_ABILITY, ability);
    }
    public static PlayChoice makeExchangePositionsChoice(PlayZonePair stagePositionPair) {
        return new PlayChoice(PlayChoiceType.EXCHANGE_POSITIONS, stagePositionPair);
    }
    public static PlayChoice makeActionChoice(PlayChoiceAction action) {
        return new PlayChoice(PlayChoiceType.CHOOSE_ACTION, action);
    }
    public static PlayChoice makeAttackChoice(AttackPositionPair attackPositionPair) {
        return new PlayChoice(PlayChoiceType.CHOOSE_ATTACK, attackPositionPair);
    }
    public static PlayChoice makeZoneChoice(PlayZone zone) {
        return new PlayChoice(PlayChoiceType.CHOOSE_ZONE, zone);
    }
    public static PlayChoice makeStringChoice(LocalizedString string) {
        return new PlayChoice(PlayChoiceType.CHOOSE_STRING, string);
    }
    public static PlayChoice makeRuleActionChoice(InterruptRuleAction interruptRuleAction) {
        return new PlayChoice(PlayChoiceType.CHOOSE_RULE_ACTION, interruptRuleAction);
    }



    public PlayChoiceType getChoiceType() {
        return choiceType;
    }

    public PlayChoiceAction getAction() {
        return action;
    }

    public PlayingCard getCard() {
        return card;
    }

    public Ability getAbility() {
        return ability;
    }

    public PlayZonePair getExchangeableStagePositions() {
        return stagePositionPair;
    }

    public AttackPositionPair getAttackPositionPair() {
        return attackPositionPair;
    }

    public PlayZone getZone() {
        return zone;
    }

    public LocalizedString getLocalizedString() {
        return string;
    }

    public InterruptRuleAction getInterruptRuleAction() {
        return interruptRuleAction;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .omitNullValues()
                .add("choiceType", choiceType)
                .add("interruptRuleAction", interruptRuleAction)
                .add("ability", ability)
                .add("stagePositionPair", stagePositionPair)
                .add("attackPositionPair", attackPositionPair)
                .add("zone", zone)
                .add("string", string)
                .add("card", card)
                .add("action", action)
                .toString();
    }
}

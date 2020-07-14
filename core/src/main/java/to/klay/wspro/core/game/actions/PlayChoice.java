package to.klay.wspro.core.game.actions;

import com.google.common.base.MoreObjects;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.LocalizedString;
import to.klay.wspro.core.game.cardLogic.ability.Ability;
import to.klay.wspro.core.game.events.InterruptRuleAction;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.zones.PlayZone;
import to.klay.wspro.core.game.proto.PlayChoiceActionProtoTypeConverter;
import to.klay.wspro.core.game.proto.PlayChoiceTypeProtoTypeConverter;
import to.klay.wspro.core.game.proto.ProtoPlayChoice;

@ProtoClass(ProtoPlayChoice.class)
public class PlayChoice {

    private static final Logger log = LogManager.getLogger();
    @ProtoField(converter = PlayChoiceTypeProtoTypeConverter.class)
    private final PlayChoiceType choiceType;
    @ProtoField
    private InterruptRuleAction interruptRuleAction;
    @ProtoField
    private Ability ability;
    @ProtoField
    private PlayZonePair stagePositionPair;
    @ProtoField
    private AttackPositionPair attackPositionPair;
    @ProtoField
    private PlayZone zone;
    @ProtoField
    private LocalizedString localizedString;
    @ProtoField
    private PlayingCard card;
    @ProtoField(converter = PlayChoiceActionProtoTypeConverter.class)
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
    private PlayChoice(PlayChoiceType choiceType, LocalizedString localizedString){
        this(choiceType);
        this.localizedString = localizedString;
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

    public PlayZonePair getStagePositionPair() {
        return stagePositionPair;
    }

    public AttackPositionPair getAttackPositionPair() {
        return attackPositionPair;
    }

    public PlayZone getZone() {
        return zone;
    }

    public LocalizedString getLocalizedString() {
        return localizedString;
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
                .add("string", localizedString)
                .add("card", card)
                .add("action", action)
                .toString();
    }
}

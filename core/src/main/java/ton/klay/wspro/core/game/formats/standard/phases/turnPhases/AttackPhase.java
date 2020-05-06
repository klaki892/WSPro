package ton.klay.wspro.core.game.formats.standard.phases.turnPhases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.phase.GamePhase;
import ton.klay.wspro.core.api.game.phase.TurnPhase;
import ton.klay.wspro.core.game.Duel;
import ton.klay.wspro.core.api.cards.abilities.components.AbilityConditions;
import ton.klay.wspro.core.game.enums.PlayTiming;
import ton.klay.wspro.core.game.events.TimingEventHandler;

public class AttackPhase extends Phase {

    private static final Logger log = LogManager.getLogger();

    private boolean firstAttack = true;

    public AttackPhase(Duel duel) {
        super(duel, TurnPhase.TURN_PHASE_ATTACK);
    }

    /**
     * Preform the Attack Phase
     */
    @Override
    public void startPhase() {

        log.debug("Beginning Attack Phase");
        duel.triggerCheck(AbilityConditions.CONDITION_START_ATTACK_PHASE);
        duel.checkTiming();
        //todo attackSubPhase and all of it's glory
        attackSubPhase();
        //todo encoreStep


    }

    @Override
    public GamePhase nextPhase() {
        return null;
    }

    /**
     * preforms the attack sub Phase
     *
     * todo put ruling for subphase
     */
    private void attackSubPhase() {

        attackDeclareStep();

    }

    /**
     * The Attack Declaration step
     * todo put ruling
     */
    private void attackDeclareStep() {
        log.debug("Starting Attack Declaration Step");
        duel.triggerCheck(AbilityConditions.CONDITION_ON_ATTACK_DECLARE_STEP_START);
        duel.checkTiming();
        //todo determine if can attack
        if (canAttack()){
            log.debug("player can attack");
            duel.playTiming(PlayTiming.PLAY_TIMING_ATTACK);
            firstAttack = false;
        }
        //todo action: determine if still attacking or if the player passed
        if (moveToTrigger()){
            triggerStep();
        }
    }

    private boolean canAttack() {
        //todo: action: see if we have any standing, attack capable cards
        //todo: also check for first turn second attack senarios.
        return false;
    }

    /**
     * Preforms the triggerEvent step of the attack phase
     * //todo ruiling
     */
    private void triggerStep() {
        log.debug("Preforming Event Step");
        duel.triggerCheck(AbilityConditions.CONDITION_ON_TRIGGER_STEP_START);
        duel.checkTiming();
        //todo: action send top card to resolution zone
        //todo: get the triggers and apply them to this attack
        duel.triggerCheck(AbilityConditions.CONDITION_ON_TRIGGER);
        duel.checkTiming();

        if (isFrontalAttack()){
            counterStep();
        } else {
            damageStep();
        }

    }

    /**
     * preforms the Counter Step of the Attack phase
     *
     * //todo ruiling
     */
    private void counterStep() {
        log.debug("Preforming Counter Step");
        duel.triggerCheck(AbilityConditions.CONDITION_ON_COUNTER_STEP_START);
        duel.checkTiming();
        duel.playTiming(PlayTiming.PLAY_TIMING_COUNTER);
        damageStep();
    }

    /**
     * preforms the Damage Step of the Attack Phase
     *
     * todo ruliing
     */
    private void damageStep() {
        log.debug("Preforming Damage Step");
        duel.triggerCheck(AbilityConditions.CONDITION_ON_DAMAGE_STEP_START);
        duel.checkTiming();
        int totalDamage = countDamage();
        if (totalDamage > 0 && stillBattling()) {
            int damageDealt = dealDamage(totalDamage);
            if (damageDealt == 0) { //canceled
                duel.triggerCheck(AbilityConditions.CONDITION_ON_DAMAGE_CANCEL);
            } else { //damage successful
                duel.triggerCheck(AbilityConditions.CONDITION_ON_DAMAGE_TAKEN);
            }
        }

        duel.checkTiming();
        if (isFrontalAttack()){
            battleStep();
        } else {
            attackDeclareStep();
        }

    }

    /**
     * preforms the battle step of the Attack Phase
     *
     * @see //todo ruiling
     */
    private void battleStep() {
        log.debug("Starting Battle Step");
        duel.triggerCheck(AbilityConditions.CONDITION_ON_BATTLE_STEP_START);
        duel.checkTiming();
        if (stillBattling()){
            doBattle();
            duel.triggerCheck(AbilityConditions.CONDITION_ON_REVERSE);
            duel.triggerCheck(AbilityConditions.CONDITION_ON_REVERSE_FROM_BATTLE);
        }

        duel.checkTiming();
        log.debug("End of the Battle");
        attackDeclareStep();
    }

    /**
     * preforms the actions for a battle between two cards accordin to the rules
     */
    private void doBattle() {
        //todo action: compare power values (look at rules) and reverse cards
        //todo log battle cards, values, say who wins, etc
    }

    /**
     * Determines if the battle is still taking place after check timings <br>
     *     The Battle can be stopped at this point if the attacking card has moved, etc.
     * @see // TODO: ruling
     * @return if the battle is still taking place after {@link TimingEventHandler#checkTiming()}
     */
    private boolean stillBattling() {
        //todo: Action: make sure the attacking card is still "attacking" according to ruiling
        return true;
    }

    /**
     * Preform the actions for dealing damage to a player
     * @param damage the amount of damage to deal to the player (if they dont cancel)
     * @return Retunrs how much damage was dealt
     */
    private int dealDamage(int damage) {
       //todo action: deal damage to the opposing player, checking for canceling
        return 0;
    }

    /**
     * checks the total damage from the soul count of the currently attacking card as well as that triggerEvent.
     * @return an int returning the damage amount
     */
    private int countDamage() {
        //todo: count the number of souls from the currently attacking card
        //todo see if we had soul triggers on the triggered card in resolution zone

        return 0;
    }

    private boolean isFrontalAttack() {
        //todo: action determine if the attacking declaration was a frontal attack
        return false;
    }

    private boolean moveToTrigger(){
        //todo: action check if we have a card thats attacking, if not return false
        return false;
    }

}

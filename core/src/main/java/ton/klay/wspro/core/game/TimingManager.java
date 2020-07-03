package ton.klay.wspro.core.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.cardLogic.ability.AutomaticAbility;
import ton.klay.wspro.core.game.events.InterruptRuleAction;
import ton.klay.wspro.core.game.events.LosingVerdictRuleAction;
import ton.klay.wspro.core.game.events.RuleAction;
import ton.klay.wspro.core.game.formats.standard.triggers.GameOverTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerCause;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles the Operation of timing effects. Interrupt, Check Timings
 */
public class TimingManager {

    private static final Logger log = LogManager.getLogger();

    private final Game game;
    private final List<RuleAction> checkTypeRuleActions = new ArrayList<>();
    private final List<InterruptRuleAction> interruptRuleActions = new ArrayList<>();
    private final List<AutomaticAbility> automaticAbilities = new ArrayList<>();
    private int interruptLocks = 0;
    private int simultaneousLocks = 0;

    public TimingManager(Game game){
        this.game = game;
    }

    public void add(RuleAction ruleAction){
        if (ruleAction instanceof InterruptRuleAction) {
            interruptRuleActions.add((InterruptRuleAction) ruleAction);
        } else {
            checkTypeRuleActions.add(ruleAction);
        }
    }

    public void add(AutomaticAbility ability){
        automaticAbilities.add(ability);
    }


    public void doCheckTiming() {

        while (true) {

            resolveRuleActions();

            //Resolve all automatic abilities.
            if (resolveAutomaticAbility(game.getCurrentTurnPlayer()))
                continue; //repeat the loop so we get the rule actions again.

            //resolve automatic abilities on non-player's side
            if(resolveAutomaticAbility(game.getNonTurnPlayer()))
                continue;

            break;
        }
    }

    private boolean resolveAutomaticAbility(GamePlayer player) {
        List<AutomaticAbility> currentPlayerActions = getPlayerActions(player);
        if (currentPlayerActions.isEmpty()) {
            return false; //no abilites left
        } else {
            resolvePlayerAction(player, currentPlayerActions);
            return true;
        }
    }

    private List<AutomaticAbility> getPlayerActions(GamePlayer player){
        return automaticAbilities.stream().filter(ability -> ability.getMaster() == player ).collect(Collectors.toList());
    }

    private void resolvePlayerAction(GamePlayer player, List<AutomaticAbility> playerActions){
        AutomaticAbility ability;

        if (playerActions.size() > 1) {
            ability = player.getController().chooseAutomaticAbilityToPerform(playerActions);
        } else {
            ability = playerActions.get(0);
        }

        automaticAbilities.remove(ability);
        ability.performEffect();
    }

    private void resolveRuleActions(){
        //repeat until no more rule actions happen
        while (!checkTypeRuleActions.isEmpty()) {

            //get all current actions and resolve them simultaneously
            ArrayList<RuleAction> currentRuleActions = new ArrayList<RuleAction>(checkTypeRuleActions);

            enableInterruptLock();
            for (RuleAction action : currentRuleActions) {
                losingVerdictCheck();
                checkTypeRuleActions.remove(action);
                action.execute();
            }
            disableInterruptLock();
            doInterruptTiming();
        }
    }

    private void losingVerdictCheck() {
        List<RuleAction> losingVerdicts = checkTypeRuleActions.stream()
                .filter(ruleAction -> ruleAction instanceof LosingVerdictRuleAction).collect(Collectors.toList());
        //if any player fulfills the condition, declare all who have the verdict to have lost the game
        if (losingVerdicts.size() != 0) {
            enableInterruptLock();
            losingVerdicts.forEach(RuleAction::execute);

            GameOverTrigger trigger = new GameOverTrigger(game.getLosingPlayers(), TriggerCause.GAME_ACTION, game.getCurrentTurnPlayer());
            game.getTriggerManager().post(trigger);
            disableInterruptLock();
            throw new GameOverException(game.getLosingPlayers());
        }
    }

    public void doInterruptTiming() {
        if (isInterruptLocked()) return;

        for(GamePlayer player : new GamePlayer[]{game.getCurrentTurnPlayer(), game.getNonTurnPlayer()}) {

            List<InterruptRuleAction> turnPlayerActions = interruptRuleActions
                    .stream().filter(action -> action.getMaster() == player).collect(Collectors.toList());

            while(!turnPlayerActions.isEmpty()) {
                //performing interrupts during the check would cause repeated actions to happen
                enableInterruptLock();

                losingVerdictCheck();
                InterruptRuleAction action;
                //the player chooses if there is more than one
                if (turnPlayerActions.size() > 1) {
                    action = player.getController().chooseInterruptRuleAction(interruptRuleActions);
                } else {
                    action = turnPlayerActions.get(0);
                }

                turnPlayerActions.remove(action);
                interruptRuleActions.remove(action);
                action.execute();
                disableInterruptLock();
            }
        }
    }

    public boolean isInterruptLocked() {
        return interruptLocks != 0;
    }
    public boolean isSimultaneousLocked() {
        return simultaneousLocks != 0;
    }

    /**
     * Adds a lock preventing {@link #doContinuousTiming()} and {@link #doInterruptTiming()}<br/>
     * By its nature, it also calls {@link #enableInterruptLock()}
     */
    public void enableSimultaneousLock() {
        enableInterruptLock();
        simultaneousLocks++;
    }
    /**
     * Removes a lock on {@link #doInterruptTiming()} and {@link #doContinuousTiming()}
     * It is <b> HIGHLY</b> recommended to call {@link #doContinuousTiming()} after doing this.
     */
    public void disableSimultaneousLock() {
        disableInterruptLock();
        if (simultaneousLocks == 0){
            log.warn("Timing Manager was told to remove a simultaneous lock when none are present.");
        }
        simultaneousLocks = Math.max(0, simultaneousLocks-1);
    }


    /**
     * Adds a lock to the {@link #doInterruptTiming()} preventing execution of {@link InterruptRuleAction}s
     */
    public void enableInterruptLock(){
        interruptLocks++;
    }

    /**
     * Removes a lock on {@link #doInterruptTiming()}
     * It is <b> HIGHLY</b> recommended to call {@link #doInterruptTiming()} after doing this.
     */
    public void disableInterruptLock(){
        if (interruptLocks == 0){
            log.warn("Timing Manager was told to remove a interrupt lock when none are present.");
        }
        interruptLocks = Math.max(0, interruptLocks-1);
    }

    public void doContinuousTiming() {
        //todo reset all cards
        /* TODO:
            continuous Effect
            If continious effect affects a specific ZONE instead of a card:
                the change is applied the moment it enters the zone.
                If the effect comes from an automatic ability, effects on zones apply before basic resolution.
            If card stats needed, basic resolve as follows:
                1. obtain base stats of card
                2. apply effects that DO NOT change the state of the card.
                3. apply effects that DO change the state of the card.
                    3a.Indepedent Effects are resolved in Fundamental Order*
                    3b.Dependent Effects* are resolved after Indepedent
                    *Dependent Effects (multiple state changes)
                        If Effect A applies a State that Effect B needs to be active:
                            Effect B is labeled a Dependent Effect and always occurs after Indepedent effects.
                            (A.K.A if an Effect requires a conditional to apply, its dependent)
                    *Fundamental Order (Multiple Indepedent state changes)
                        If a Continuous Effect comes from Continuous Ability:
                            When the card is placed on its current zone is the fundemental order of that card.
                            If character on stage owns the cont ability, when that character was placed on that specific stage position from another zone is it's fundamental order.
                            Else, when the ability is played is its fundamental order.
         */
    }
}

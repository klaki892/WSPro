package ton.klay.wspro.core.game.events;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.abilities.components.AbilityConditions;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.Duel;
import ton.klay.wspro.core.game.actions.GameAction;
import ton.klay.wspro.core.game.cardLogic.ability.AutomaticAbility;
import ton.klay.wspro.core.game.enums.PlayTiming;
import ton.klay.wspro.core.game.formats.standard.cards.WeissCard;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Klayton Killough
 * Date: 8/13/2017
 *
 * Class that handles all
 */
public class TimingEventHandler {

    private static final Logger log = LogManager.getLogger();


    private Duel duel;
    private List<RuleAction> pendingRuleActions;
    private List<StandbyAbility> standbyAbilityList;

    public TimingEventHandler(Duel duel){
        this.duel = duel;
        pendingRuleActions = new ArrayList<RuleAction>();
        standbyAbilityList = new ArrayList<>();
        log.debug("Timing Event Handler Created");
    }

    /**
     * Adds a card to the standBy Queue
     * @param registeredAbility - the {@link AutomaticAbility} wrapped with player info to add to the list of Abilites that are in Standby
     */
    public void addStandbyCondition(StandbyAbility registeredAbility) {
        standbyAbilityList.add(registeredAbility);
    }

    /**
     * Resolves all rule actions as specified in Weiss Schwarz Rule Steps
     * @see <code>Weiss Schwarz Rules 8.5.1.1</code>
     * @throws InterruptRuleAction
     */
    public void resolveRuleActions() throws InterruptRuleAction{
        //Resolve all rule actins
        while (!pendingRuleActions.isEmpty()){
            log.debug("There exists " + pendingRuleActions.size() + " rule actions. Resolving.");
            //todo resolve rule actions, if more are generated from them running, rinse and repeat
        }

    }

    /**
     * Resolves an ability owned the by the specified player
     * @param abilityListOwner
     * @throws InterruptRuleAction - if an interrupt rule action occured during resoution
     * @return if an ability was resolved.
     */
    public boolean resolveAutomaticAbility(GamePlayer abilityListOwner) throws InterruptRuleAction{
        //from the list of abilities get all that are owned by the player

        List<StandbyAbility> playerOwnedAbilities = new ArrayList<>();

        for (StandbyAbility ability : standbyAbilityList){
            if (ability.cardOwner.equals(abilityListOwner))
                playerOwnedAbilities.add(ability);
        }

        if (!playerOwnedAbilities.isEmpty()){
            log.debug(abilityListOwner + " has " + playerOwnedAbilities.size() + " abilities primed, starting resolution.");
            //todo allow the player to choose one of the abiltiies and resolve it.
            //todo remove the ability the player chose from the main list.

            return true;
        } else
            return false; //no ability was resolved.

    }


    /**
     * Preforms a Check Timing as defined by the Weiss Rules, and also does other game-state updates at the same time.
     * @see <code>Weiss Schwarz Rules 8.5.1</code>
     */
    public void checkTiming() throws InterruptRuleAction {
        log.debug("Starting checkTiming");

        while(true) {
            //Resolve all rule actions
            resolveRuleActions();

            //Resolve all automatic abilities in Standby Condition.
            if (resolveAutomaticAbility(duel.currentTurnPlayer))
                continue; //repeat the loop so we get the rule actions again.

            //resolve automatic abilities on non-player's side
            if (resolveAutomaticAbility(duel.opposingPlayer))
                continue;

            break;
        }

        //end of check timing
        log.debug("Check Timing Ended");
    }

    /**
     * Preforms a play timing, a {@link GameAction} Where we need specific input from the player to proceed. <br/>
     * More than likely also preforms {@link #checkTiming()}s continuously as the effect takes place
     * @param playTiming the type of play timing occuring at this specific moment in the game. defines how to interact with the players.
     */
    public void playTiming(PlayTiming playTiming) throws InterruptRuleAction{
        log.debug("starting playtiming for " + playTiming);

        switch (playTiming){

            case PLAY_TIMING_MAIN:
                //todo: Action: wait for user response to preform the action
                //todo: based on use response form an action from one of the following 4
                // playchar
                // playEvent
                //Use ACt
                //Move Cards
                //endTiming
                break;

            case PLAY_TIMING_COUNTER: {
                //todo action: ask the user if they wish to counter
                Boolean backup = null;

                if (backup) {
                    //todo: action: have the user select a backup card
                    //todo: action: preform backup code

                    duel.triggerCheck(AbilityConditions.CONDITION_ON_BACKUP);
                    checkTiming();
                }
                break;
            }

            case PLAY_TIMING_ATTACK:{
                //todo action: have the player choose an attack card
                    //todo catch if the player cancels the player passes.
                //todo action: have the player choose a attack declartion
                log.debug("Player chose Attack Type: "); //todo finish this log
                //todo action: set the battle cards to their respective states
                duel.triggerCheck(AbilityConditions.CONDITION_ON_ATTACK);
                duel.triggerCheck(AbilityConditions.CONDITION_BEING_ATTACKED);
                checkTiming();
                //todo action: restAttackingCard
                duel.triggerCheck(AbilityConditions.CONDITION_ON_REST);
                checkTiming();
                //the rest is handled back at the triggerEvent step
                break;
            }


            default:
                IllegalArgumentException ex = new IllegalArgumentException(playTiming.name());
                log.error("A playtiming hasnt been coded.");
                log.trace(ex);
                throw ex;
        }
    }

    /**
     * Holds the registered abilities to be fired with a reference to the owning card
     */
    class StandbyAbility {
        private String cardOwner;
        private AutomaticAbility automaticAbility;
        private WeissCard owningCard;

        StandbyAbility(WeissCard owningCard, AutomaticAbility automaticAbility){
            this.owningCard = owningCard;
            this.automaticAbility = automaticAbility;
            cardOwner = owningCard.getOwner();
        }

        public WeissCard getOwningCard() {
            return owningCard;
        }

        public AutomaticAbility getAutomaticAbility() {
            return automaticAbility;
        }
    }

    public StandbyAbility generateStandByAbility(WeissCard owningCard, AutomaticAbility automaticAbility){
        return new StandbyAbility(owningCard, automaticAbility);
    }

}

package ton.klay.wspro.core.game.events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.cards.GameCard;
import ton.klay.wspro.core.api.cards.abilities.components.AbilityConditions;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.cardLogic.ability.AutomaticAbility;
import ton.klay.wspro.core.game.formats.standard.cards.WeissCard;

import java.util.ArrayList;

/**
 * Object that maintains active records of what card is listening for what event to take place. <br>
 * Works closely with {@link AbilityConditions} to fire off events into the Standby condition queue
 */
public class ConditionHandler {
    private static final Logger log = LogManager.getLogger();

    private TimingEventHandler timingEventHandler;
    private ArrayList<TimingEventHandler.StandbyAbility> registeredAbilities;

    /**
     * Creates a new condition handler for a game
     * @param timingEventHandler a reference to the timing event handler for where to add automatic abilities once they enter the standby condition
     */
    public ConditionHandler(TimingEventHandler timingEventHandler){
        registeredAbilities = new ArrayList<>();
        this.timingEventHandler = timingEventHandler;
        log.debug("Condition Handler Created");
    }

    /**
     * Registers an {@link AutomaticAbility} for the checking for it's triggerEvent condition. Keeping a reference of the card which contains the ability for information purposes
     * @param card - the card that owns the ability
     * @param ability - the automatic ability that needs a condition to be triggered (all of them)
     * @return If the ConditionHandler has successfully registered the ability and is ready to prime the ability when the condition applies.
     */
    public boolean registerAbility(WeissCard card, AutomaticAbility ability) {
        TimingEventHandler.StandbyAbility registeredAbility = timingEventHandler.generateStandByAbility(card, ability);
        registeredAbilities.add(registeredAbility);
        log.debug("Registered Ability " + ability + " for card " + card);
        return  true;
    }

    /**
     * Removes a an {@link AutomaticAbility} for the checking for it's triggerEvent condition. Keeping a reference of the card which contains the ability for information purposes
     * @param card - the card that owns the ability
     * @param ability - the automatic ability that needs a condition to be triggered (all of them)
     * @return If the ConditionHandler has successfully removed the registered ability
     */
    public boolean removeAbility(GameCard card, AutomaticAbility ability) {

        //FIXME because there can be A LOT of abiltiies registered at once, we NEED a more efficient search (Not O(n) )
        for (TimingEventHandler.StandbyAbility registeredAbility : registeredAbilities){
            if (registeredAbility.getOwningCard().equals(card) && registeredAbility.getAutomaticAbility().equals(ability)) {
                registeredAbilities.remove(registeredAbility);
                return true;
            }
        }

        log.error("Failed to de-register the ability ' " + ability + " ' from card ' " + card + "', doesnt exist in registered list");
        return false;
    }



    /**
     * Checks all listening abiltiies for the Condition that was primed. <br>
     *     Adds all abilities that were listening for the specific triggerEvent to be the in the Standby Queue
     * @param condition - the condition for which to prime all abiltiies that listen for it.
     *  @see  AbilityConditions
     */
    public void fireCondition(AbilityConditions condition) {

        for (TimingEventHandler.StandbyAbility registeredAbility : registeredAbilities){
            AutomaticAbility ability = registeredAbility.getAutomaticAbility();
            GameCard owningCard = registeredAbility.getOwningCard();
            String cardOwner = registeredAbility.getOwningCard().getOwner();

            if (registeredAbility.getAutomaticAbility().getCondition().equals(condition)) {
                log.debug(cardOwner + "'s " + owningCard + " Automatic Ability(" + ability + ") just primed from " + condition);
                timingEventHandler.addStandbyCondition(registeredAbility);
            }
        }
    }


}

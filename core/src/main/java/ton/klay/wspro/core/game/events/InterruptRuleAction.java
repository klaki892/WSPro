package ton.klay.wspro.core.game.events;

import ton.klay.wspro.core.game.enums.RuleActionReason;

/**
 * @author Klayton Killough
 * Date: 8/13/2017
 * //todo javadoc
 */
public class InterruptRuleAction extends Exception {

    public RuleActionReason interruptReason;

    public InterruptRuleAction(RuleActionReason reason){

    }

    public RuleActionReason getInterruptReason() {
        return interruptReason;
    }

    /**
     * returns the reason for the game interruption in verbose String form.
     *
     * @return returns the reason for the game interruption in verbose form.
     */
    public String getMessage() {
        return setMessage(interruptReason);
    }

    private String setMessage(RuleActionReason reason) {
        switch (reason){
            //todo fill me up with Rule actions for handling

            default:
                return "Interrupt occured for: " + reason.name();
        }
    }
}

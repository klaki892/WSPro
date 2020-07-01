package ton.klay.wspro.core.api.game.player;

import ton.klay.wspro.core.game.cardLogic.ability.AutomaticAbility;
import ton.klay.wspro.core.game.events.InterruptRuleAction;

import java.util.List;

public interface PlayerController {

    InterruptRuleAction chooseInterruptRuleAction(List<InterruptRuleAction> interruptRuleActions);

    AutomaticAbility chooseAutomaticAbilityToPerform(List<AutomaticAbility> interruptRuleActions);
}

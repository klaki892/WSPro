package ton.klay.wspro.core.api.game.player;

import ton.klay.wspro.core.game.actions.PlayChoice;
import ton.klay.wspro.core.game.cardLogic.ability.AutomaticAbility;
import ton.klay.wspro.core.game.events.InterruptRuleAction;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

import java.util.List;
import java.util.Optional;

public interface PlayerController {

    InterruptRuleAction chooseInterruptRuleAction(List<InterruptRuleAction> interruptRuleActions);

    AutomaticAbility chooseAutomaticAbilityToPerform(List<AutomaticAbility> interruptRuleActions);

    Optional<PlayingCard> chooseClockCard(List<PlayingCard> cards);

    Optional<PlayingCard> chooseClimaxPhaseCard(List<PlayingCard> climaxCards);

    PlayingCard chooseLevelUpCard(List<PlayingCard> clockCards);

    boolean confirmAbilityUsage();

    PlayChoice makePlayChoice(List<PlayChoice> playChoices);
}

package ton.klay.wspro.core.game.formats.standard.phases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.CardOrientation;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.actions.PlayChoice;
import ton.klay.wspro.core.game.actions.PlayChoiceAction;
import ton.klay.wspro.core.game.actions.PlayChoiceType;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.commands.Commands;
import ton.klay.wspro.core.game.formats.standard.triggers.CardClockedTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import ton.klay.wspro.core.game.formats.standard.zones.PlayZone;

import java.util.List;
import java.util.stream.Collectors;

public class ClockPhase extends BasePhase  {

    Logger log = LogManager.getLogger();

    public ClockPhase(PhaseHandler phaseHandler, GamePlayer turnPlayer) {
        super(phaseHandler, turnPlayer, TurnPhase.CLOCK_PHASE);
    }

    @Override
    public void startPhase() {
        super.startPhase();

        PlayZone clock = turnPlayer.getPlayArea().getPlayZone(Zones.ZONE_CLOCK);
        PlayZone hand = turnPlayer.getPlayArea().getPlayZone(Zones.ZONE_HAND);
        List<PlayingCard> handContents = hand.getContents();

        //create the player choice
        List<PlayChoice> choices = handContents.stream().map(PlayChoice::makeCardChoice).collect(Collectors.toList());

        //Player doesnt have to clock
        choices.add(PlayChoice.makeActionChoice(PlayChoiceAction.END_ACTION));

        PlayChoice choice = Commands.makeSinglePlayChoice(turnPlayer, choices);

        if (choice.getChoiceType() == PlayChoiceType.CHOOSE_CARD){
            PlayingCard cardToClock = choice.getCard();

            Commands.moveCard(cardToClock, hand, clock, Commands.Utilities.getTopOfZoneIndex(clock),
                    CardOrientation.STAND, clock.getVisibility(), TriggerCause.GAME_ACTION, this);

            //issue event
            CardClockedTrigger trigger = new CardClockedTrigger(turnPlayer, cardToClock, TriggerCause.GAME_ACTION, this);
            game.getTriggerManager().post(trigger);
            game.continuousTiming();

            Commands.drawCard(turnPlayer, TriggerCause.GAME_ACTION, this);
            Commands.drawCard(turnPlayer, TriggerCause.GAME_ACTION, this);
        }

        game.checkTiming();
    }

}

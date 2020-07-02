package ton.klay.wspro.core.game.formats.standard.phases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.CardOrientation;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.commands.Commands;
import ton.klay.wspro.core.game.formats.standard.triggers.CardClockedTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerCause;

import java.util.List;
import java.util.Optional;

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

        Optional<PlayingCard> clockCard = turnPlayer.getController().chooseClockCard(handContents);

        if (clockCard.isPresent()){
            Commands.moveCard(clockCard.get(), hand, clock, Commands.Utilities.getTopOfZoneIndex(clock),
                    CardOrientation.STAND, clock.getVisibility(), TriggerCause.GAME_ACTION, this);

            //issue event
            CardClockedTrigger trigger = new CardClockedTrigger(turnPlayer, clockCard.get(), TriggerCause.GAME_ACTION, this);
            game.getTriggerManager().post(trigger);
            game.continuousTiming();

            Commands.drawCard(turnPlayer, TriggerCause.GAME_ACTION, this);
            Commands.drawCard(turnPlayer, TriggerCause.GAME_ACTION, this);
        }

        game.checkTiming();
    }

}

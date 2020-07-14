package to.klay.wspro.core.game.formats.standard.phases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.CardOrientation;
import to.klay.wspro.core.api.game.field.Zones;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.actions.PlayChoice;
import to.klay.wspro.core.game.actions.PlayChooser;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.commands.Commands;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import to.klay.wspro.core.game.formats.standard.zones.PlayZone;

import java.util.List;
import java.util.stream.Collectors;

public class EndPhase extends BasePhase  {

    Logger log = LogManager.getLogger();

    public EndPhase(PhaseHandler phaseHandler, GamePlayer turnPlayer) {
        super(phaseHandler, turnPlayer, TurnPhase.END_PHASE);
    }

    @Override
    public void startPhase() {
        super.startPhase();

        PlayZone hand = turnPlayer.getPlayArea().getPlayZone(Zones.ZONE_HAND);
        PlayZone climax = turnPlayer.getPlayArea().getPlayZone(Zones.ZONE_CLIMAX);
        PlayZone waitingRoom = turnPlayer.getPlayArea().getPlayZone(Zones.ZONE_WAITING_ROOM);

        boolean somethingRemains = false;
        do {
            //hand size check
            if (hand.size() > 7){
                int numToDiscard = hand.size() - 7;

                //ask player for discard choices
                List<PlayChoice> choices = hand.getContents().stream().map(PlayChoice::makeCardChoice).collect(Collectors.toList());
                PlayChooser chooser = new PlayChooser(choices, PlayChooser.SelectionType.MULTI, numToDiscard);
                List<PlayingCard> chosenCards = Commands.makePlayChoice(turnPlayer, chooser)
                        .stream().map(PlayChoice::getCard).collect(Collectors.toList());

                chosenCards.forEach(card -> {
                    Commands.discardCard(turnPlayer, card, TriggerCause.GAME_ACTION, this);
                });
            }

            if (climax.size() > 0){
                Commands.moveCard(climax.getContents().get(0), climax, waitingRoom,
                        Commands.Utilities.getTopOfZoneIndex(waitingRoom), CardOrientation.STAND,
                        waitingRoom.getVisibility(), TriggerCause.GAME_ACTION, this);
            }

            game.checkTiming();

            somethingRemains = ((hand.size() > 7));

        } while (somethingRemains);
        phaseHandler.advanceTurnPlayer();
    }

}

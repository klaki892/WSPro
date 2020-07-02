package ton.klay.wspro.core.game.formats.standard.phases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.CardOrientation;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.formats.standard.commands.Commands;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerCause;

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
            while (hand.size() > 7){
                //FIXME build playtiming as described in ClimaxPhase
                Commands.discardCard(turnPlayer, hand.getContents().get(0), TriggerCause.GAME_ACTION, this);
            }

            if (climax.size() > 0){
                Commands.moveCard(climax.getContents().get(0), climax, waitingRoom,
                        Commands.Utilities.getTopOfZoneIndex(waitingRoom), CardOrientation.STAND,
                        waitingRoom.getVisibility(), TriggerCause.GAME_ACTION, this);
            }

            game.checkTiming();

            somethingRemains = ((hand.size() > 7));

        } while (somethingRemains);
        phaseHandler.setCurrentTurnPlayer(phaseHandler.getNextTurnPlayer());
    }

}

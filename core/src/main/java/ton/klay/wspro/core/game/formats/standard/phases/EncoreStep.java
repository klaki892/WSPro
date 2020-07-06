package ton.klay.wspro.core.game.formats.standard.phases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.CardOrientation;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.actions.PlayChoice;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.commands.Commands;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerCause;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class EncoreStep extends BasePhase  {

    Logger log = LogManager.getLogger();

    public EncoreStep(PhaseHandler phaseHandler, GamePlayer turnPlayer) {
        super(phaseHandler, turnPlayer, TurnPhase.ENCORE_STEP);
    }

    @Override
    public void startPhase() {
        super.startPhase();
        /* TODO:
            3.	Turn player Repeat (for all owned reversed characters):
                	Choose character, put into waiting room
                	Check timing
            4.	Non-turn player performs all of #3 for 1 character, then go back to step 3
            5.	Check timing
            6.	Go back to Step 3 if reversed characters on any player stage
            7.	Advance to end phase
        */

        //repeat as long as characters are reversed on the game boad
        Collection<PlayZone> turnPlayerStage = turnPlayer.getPlayArea().getPlayZones(Zones.ZONE_STAGE);
        Collection<PlayZone> opponentStage = phaseHandler.getNonTurnPlayer().getPlayArea().getPlayZones(Zones.ZONE_STAGE);


        while (doReversedCharactersExist(turnPlayerStage, opponentStage)){
            processReversedCards(turnPlayer, turnPlayerStage);
            processReversedCards(phaseHandler.getNonTurnPlayer(), opponentStage);
            game.checkTiming();
        }
    }

    private void processReversedCards(GamePlayer player, Collection<PlayZone> stage) {
        List<PlayChoice> choices;
        HashMap<PlayingCard, PlayZone> cardZoneMapping = new HashMap<>();
        PlayZone waitingRoom = player.getPlayArea().getPlayZone(Zones.ZONE_WAITING_ROOM);

        do {
            choices = new ArrayList<>();
            for (PlayZone zone : stage) {
                for (PlayingCard card : zone.getContents()) {
                    if (card.getOrientation() == CardOrientation.REVERSED) {
                        choices.add(PlayChoice.makeCardChoice(card));
                        cardZoneMapping.put(card, zone);
                    }
                }
            }
            //ask the player to pick a card to send to waiting room
            if (choices.size() > 0){
                PlayingCard card = Commands.makeSinglePlayChoice(player, choices).getCard();
                PlayZone sourceZone = cardZoneMapping.get(card);
                Commands.moveCard(card, sourceZone, waitingRoom, Commands.Utilities.getTopOfZoneIndex(waitingRoom),
                        CardOrientation.STAND, waitingRoom.getVisibility(), TriggerCause.GAME_ACTION, this);
                game.checkTiming();
            }
        } while (choices.size() > 0);
    }

    private boolean doReversedCharactersExist(Collection<PlayZone> turnPlayerStage, Collection<PlayZone> opponentStage) {
        ArrayList<PlayZone> stageZones = new ArrayList<>(turnPlayerStage);
        stageZones.addAll(opponentStage);

        for (PlayZone stageZone : stageZones) {
            for (PlayingCard card : stageZone.getContents()) {
                if (card.getOrientation() == CardOrientation.REVERSED)
                    return true;
            }
        }
        return false;
    }

}

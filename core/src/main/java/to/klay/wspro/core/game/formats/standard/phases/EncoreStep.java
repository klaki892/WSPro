/*******************************************************************************
 *  WSPro - Trading Card Game Simulator
 *  Copyright (C) 2020  Klayton Killough
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published
 *  by the Free Software Foundation, version 3 of the License.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

package to.klay.wspro.core.game.formats.standard.phases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.CardOrientation;
import to.klay.wspro.core.api.game.field.Zones;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.actions.PlayChoice;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.commands.Commands;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import to.klay.wspro.core.game.formats.standard.zones.PlayZone;

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

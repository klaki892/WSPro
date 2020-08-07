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

package to.klay.wspro.core.game.events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.CardOrientation;
import to.klay.wspro.core.api.game.GameRuntimeException;
import to.klay.wspro.core.api.game.field.Zones;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.commands.Commands;
import to.klay.wspro.core.game.formats.standard.triggers.RefreshPointAddedTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import to.klay.wspro.core.game.formats.standard.zones.PlayZone;

public class ReshuffleRuleAction extends InterruptRuleAction {

    private static final Logger log = LogManager.getLogger();

    private final PlayZone deck = player.getPlayArea().getPlayZone(Zones.ZONE_DECK);
    private final PlayZone waitingRoom = player.getPlayArea().getPlayZone(Zones.ZONE_WAITING_ROOM);
    private final PlayZone resolution = player.getPlayArea().getPlayZone(Zones.ZONE_RESOLUTION);


    public ReshuffleRuleAction(GamePlayer player){
        super(player);
    }

    @Override
    public void execute() {
        if (waitingRoom.size() == 0  ){
            //FIXME if in Damage process &  no climax in resolution, Immediate game lost
            // if not in damage process, create temporary listener for cards to be back in waiting room,
            // then reissue reshuffle
            throw new GameRuntimeException("Empty zones during reshuffle not implemented");
        }

        //move waiting room into deck
        for (PlayingCard card : waitingRoom.getContents()) {
            Commands.moveCard(card, waitingRoom, deck, 0, CardOrientation.STAND, deck.getVisibility(),
                    TriggerCause.GAME_ACTION, this);
        }

        Commands.shuffleZone(deck, TriggerCause.GAME_ACTION, this);
        RefreshPointAddedTrigger trigger = new RefreshPointAddedTrigger(player, TriggerCause.GAME_ACTION, this);
        game.getTriggerManager().post(trigger);
        game.continuousTiming();
    }

}

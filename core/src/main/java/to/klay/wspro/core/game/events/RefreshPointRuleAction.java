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
import to.klay.wspro.core.api.game.field.Zones;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.commands.Commands;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import to.klay.wspro.core.game.formats.standard.zones.PlayZone;

public class RefreshPointRuleAction implements RuleAction{

    private static final Logger log = LogManager.getLogger();
    private final GamePlayer player;

    public RefreshPointRuleAction(GamePlayer player) {

        this.player = player;
    }

    @Override
    public void execute() {

        //move top card of deck to clock
        PlayZone deck = player.getPlayArea().getPlayZone(Zones.ZONE_DECK);
        PlayZone clock = player.getPlayArea().getPlayZone(Zones.ZONE_CLOCK);
        PlayingCard topCard = deck.getContents().get(Commands.Utilities.getTopOfZoneIndex(deck));
        Commands.moveCard(topCard, deck, clock, Commands.Utilities.getTopOfZoneIndex(clock), CardOrientation.STAND,
                clock.getVisibility(), TriggerCause.GAME_ACTION, player);

    }
}

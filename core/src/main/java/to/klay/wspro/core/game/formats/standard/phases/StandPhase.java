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
import to.klay.wspro.core.game.formats.standard.commands.Commands;
import to.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import to.klay.wspro.core.game.formats.standard.triggers.TurnStartedTrigger;
import to.klay.wspro.core.game.formats.standard.zones.PlayZone;

public class StandPhase extends BasePhase  {

    Logger log = LogManager.getLogger();

    public StandPhase(PhaseHandler phaseHandler, GamePlayer turnPlayer) {
        super(phaseHandler, turnPlayer, TurnPhase.STAND_PHASE);
    }

    @Override
    public void beforePhase() {
        //announce start of turn
        BaseTrigger trigger = new TurnStartedTrigger(turnPlayer, TriggerCause.GAME_ACTION, this);
        game.getTriggerManager().post(trigger);
        game.continuousTiming();
        game.incrementTurnCount();

        super.beforePhase();
    }

    @Override
    public void startPhase() {
        super.startPhase();

        for (PlayZone stagePosition : turnPlayer.getPlayArea().getPlayZones(Zones.ZONE_CENTER_STAGE)) {
            stagePosition.getContents().forEach(card -> {
                Commands.changeCardOrientation(card, CardOrientation.STAND, TriggerCause.GAME_ACTION, this);
            });
        }
        game.checkTiming();
    }

}

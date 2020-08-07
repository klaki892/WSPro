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

package to.klay.wspro.core.game.formats.standard.triggers;

public enum TriggerName {
    CARD_MOVED,
    CARD_DRAWN,
    CARD_DISCARDED,
    ZONE_SHUFFLED,
    PLAYER_LOST,
    GAME_OVER,
    DECK_EMPTY,
    REFRESH_POINT_ADDED,
    TURN_STARTED,
    CARD_ORIENTED,
    PHASE_START,
    CLOCK_FULL,
    LEVELED_UP,
    CARD_CLOCKED,
    CARD_ENCORED,
    CARD_PLAYED,
    DAMAGE_PROCESSED,
    END_OF_ATTACK,
    TRIGGER_CARD_CHECKED,
    WILL_ATTACK,
    REVERSED_IN_BATTLE,
    COST_PAID,
    WILL_PAY_STOCK, ATTACK_SETUP
}

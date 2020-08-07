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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * The Phases of a turn during a normal game<br/>
 *  Also Includes the sub-sets for the Attack Phase.
 */
public enum TurnPhase {

    STAND_PHASE,
    DRAW_PHASE,
    CLOCK_PHASE,
    MAIN_PHASE,
    CLIMAX_PHASE,
    ATTACK_PHASE,
    ATTACK_DECLARATION_STEP,
    TRIGGER_STEP,
    COUNTER_STEP,
    DAMAGE_STEP,
    BATTLE_STEP,
    ENCORE_STEP,
    END_PHASE;


    public static final List<TurnPhase> PHASE_LIST = Collections.unmodifiableList(
            Arrays.asList(
                    STAND_PHASE, DRAW_PHASE, CLOCK_PHASE,
                    MAIN_PHASE, CLIMAX_PHASE, ATTACK_PHASE,
                    ATTACK_DECLARATION_STEP, TRIGGER_STEP,
                    COUNTER_STEP, DAMAGE_STEP, BATTLE_STEP,
                    ENCORE_STEP, END_PHASE
            )
    );

    /**
     * Get the next known phase in the standard turn order.
     * @return the phase that typically follows the given phase
     */
    public static TurnPhase getNext(TurnPhase phase){
        int nextIndex = PHASE_LIST.indexOf(phase) + 1;
        //support turn wraparound
        return PHASE_LIST.get(nextIndex >= PHASE_LIST.size() ? 0 : nextIndex);
    }
}

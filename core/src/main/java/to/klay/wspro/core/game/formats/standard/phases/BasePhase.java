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

import com.google.common.eventbus.EventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.game.phase.GamePhase;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.Game;
import to.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.PhaseStartedTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;

public abstract class BasePhase implements GamePhase {

    private static final Logger log = LogManager.getLogger();
    private final TurnPhase phaseIdentifier;

    protected final PhaseHandler phaseHandler;
    protected final GamePlayer turnPlayer;
    protected final Game game;
    protected final EventBus triggerSystem;


    public BasePhase(PhaseHandler phaseHandler, GamePlayer turnPlayer, TurnPhase phaseIdentifier) {
        this.phaseHandler = phaseHandler;
        this.turnPlayer = turnPlayer;
        this.phaseIdentifier = phaseIdentifier;
        game = this.phaseHandler.getGame();
        triggerSystem = game.getTriggerManager();

    }

    /**
     * Declares the start of the phase and performs the corresponding {@link Game#checkTiming()}
     */
    protected void beforePhase() {
        BaseTrigger trigger = new PhaseStartedTrigger(turnPlayer, getIdentifier(), TriggerCause.GAME_ACTION, this);
        triggerSystem.post(trigger);
        game.continuousTiming();
        game.interruptTiming();
        game.checkTiming();
    }

    /**
     * Starts executing the instructions for this phase.
     * <br/> Calls {@link #beforePhase()} before any executed actions
     */
    @Override
    public  void startPhase() {
        beforePhase();
    }

    @Override
    public TurnPhase getIdentifier() {
        return phaseIdentifier;
    }

    @Override
    public GamePlayer getMaster() {
        return turnPlayer;
    }
}

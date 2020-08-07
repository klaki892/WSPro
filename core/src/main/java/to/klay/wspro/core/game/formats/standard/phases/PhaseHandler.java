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
import to.klay.wspro.core.api.game.GameStatus;
import to.klay.wspro.core.api.game.phase.GamePhase;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.Game;
import to.klay.wspro.core.game.GameOverException;
import to.klay.wspro.core.game.actions.Combat;

/**
 * Manages the flow of a game by holding state information and transitions as the game progresses.
 */
public class PhaseHandler {

    private static final Logger log = LogManager.getLogger();

    private final Game game;

    private GamePlayer currentTurnPlayer;
    private GamePlayer nonTurnPlayer;
    private GamePlayer nextTurnPlayer;
    private TurnPhase nextPhase;
    private GamePhase currentPhase;

    private int turnNumber = 0;
    private Combat combat;
    private boolean attackedThisTurn;

    public PhaseHandler(Game game) {
        this.game = game;
    }

    public void startFirstTurn(GamePlayer firstTurnPlayer, GamePlayer nextTurnPlayer){

        nonTurnPlayer = nextTurnPlayer;
        setCurrentTurnPlayer(firstTurnPlayer);
        currentPhase = new StandPhase(this, currentTurnPlayer);
        nextPhase = TurnPhase.getNext(currentPhase.getIdentifier());
        game.setGameStatus(GameStatus.PLAYING);

        //main game execution
        while (!game.isGameOver()){

            try {
                currentPhase.startPhase();
                advanceToNextPhase();
            } catch (GameOverException gameOver){
                game.endGame();
            }
        }

    }

    public TurnPhase getNextPhase() {
        return nextPhase;
    }

    public void setNextPhase(TurnPhase nextPhase) {
        this.nextPhase = nextPhase;
    }

    public void advanceToNextPhase(){

        switch (nextPhase){
            case STAND_PHASE:
                currentPhase = new StandPhase(this, currentTurnPlayer);
                break;
            case DRAW_PHASE:
                currentPhase = new DrawPhase(this, currentTurnPlayer);
                break;
            case CLOCK_PHASE:
                currentPhase = new ClockPhase(this, currentTurnPlayer);
                break;
            case MAIN_PHASE:
                currentPhase = new MainPhase(this, currentTurnPlayer);
                break;
            case CLIMAX_PHASE:
                currentPhase = new ClimaxPhase(this, currentTurnPlayer);
                break;
            case ATTACK_PHASE:
                currentPhase = new AttackPhase(this, currentTurnPlayer);
                break;
            case ATTACK_DECLARATION_STEP:
                currentPhase = new AttackDeclarationStep(this, currentTurnPlayer);
                break;
            case TRIGGER_STEP:
                currentPhase = new TriggerStep(this, currentTurnPlayer);
                break;
            case COUNTER_STEP:
                currentPhase = new CounterStep(this, currentTurnPlayer);
                break;
            case DAMAGE_STEP:
                currentPhase = new DamageStep(this, currentTurnPlayer);
                break;
            case BATTLE_STEP:
                currentPhase = new BattleStep(this, currentTurnPlayer);
                break;
            case ENCORE_STEP:
                currentPhase = new EncoreStep(this, currentTurnPlayer);
                break;
            case END_PHASE:
                currentPhase = new EndPhase(this, currentTurnPlayer);
                break;
        }
        nextPhase = TurnPhase.getNext(nextPhase);
    }


    public GamePlayer getCurrentTurnPlayer() {
        return currentTurnPlayer;
    }

    public void setCurrentTurnPlayer(GamePlayer currentTurnPlayer) {
        this.currentTurnPlayer = currentTurnPlayer;
        setNextTurnPlayer(nonTurnPlayer);
    }

    public Game getGame() {
        return game;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public void setTurnNumber(int turnNumber) {
        this.turnNumber = turnNumber;
    }

    public GamePlayer getNonTurnPlayer() {
        return nonTurnPlayer;
    }

    public void setNextTurnPlayer(GamePlayer nextTurnPlayer) {
        this.nextTurnPlayer = nextTurnPlayer;
    }

    public GamePlayer getNextTurnPlayer() {
        return nextTurnPlayer;
    }

    public void setCombat(Combat combat) {
        this.combat = combat;
    }

    public Combat getCombat() {
        return combat;
    }

    public boolean didAttackThisTurn() {
        return attackedThisTurn;
    }

    public void setAttackedThisTurn(boolean attackedThisTurn) {
        this.attackedThisTurn = attackedThisTurn;
    }


    public void advanceTurnPlayer() {

        nonTurnPlayer = (nextTurnPlayer == currentTurnPlayer) ? nonTurnPlayer : currentTurnPlayer;
        setCurrentTurnPlayer(nextTurnPlayer);

    }
}

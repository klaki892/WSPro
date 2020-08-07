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
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.actions.Combat;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.commands.Commands;
import to.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.EndOfAttackTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.ReversedInBattleTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;

import static to.klay.wspro.core.api.cards.CardOrientation.REVERSED;

public class BattleStep extends BasePhase  {

    Logger log = LogManager.getLogger();

    public BattleStep(PhaseHandler phaseHandler, GamePlayer turnPlayer) {
        super(phaseHandler, turnPlayer, TurnPhase.BATTLE_STEP);
    }

    @Override
    public void startPhase() {
        super.startPhase();
        phaseHandler.setNextPhase(TurnPhase.ATTACK_DECLARATION_STEP);
        Combat combat = phaseHandler.getCombat();

        if (combat.getAttackingCharacter().isPresent() && combat.getDefendingCharacter().isPresent()){
            PlayingCard attacker = combat.getAttackingCharacter().get();
            PlayingCard defender = combat.getDefendingCharacter().get();

            //compare power and reverse respective characters
            if(attacker.getPower() > defender.getPower()){
                if (defender.isReversable()){
                    Commands.changeCardOrientation(defender, REVERSED, TriggerCause.GAME_ACTION, this);
                }
            } else if (attacker.getPower() < defender.getPower()){
                if (attacker.isReversable()){
                    Commands.changeCardOrientation(attacker, REVERSED, TriggerCause.GAME_ACTION, this);
                }
            } else {
                //draw, everyone's reversed
                if (attacker.isReversable()){
                    Commands.changeCardOrientation(attacker, REVERSED, TriggerCause.GAME_ACTION, this);
                }
                if (defender.isReversable()){
                    Commands.changeCardOrientation(defender, REVERSED, TriggerCause.GAME_ACTION, this);
                }
            }

            if (attacker.getOrientation() == REVERSED){
                BaseTrigger trigger = new ReversedInBattleTrigger(attacker, defender, TriggerCause.GAME_ACTION, this);
                triggerSystem.post(trigger);
            }
            if (defender.getOrientation() == REVERSED){
                BaseTrigger trigger = new ReversedInBattleTrigger(defender, attacker, TriggerCause.GAME_ACTION, this);
                triggerSystem.post(trigger);
            }
            game.continuousTiming();
            game.interruptTiming();
        }

        game.checkTiming();

        //announce end of attack
        BaseTrigger trigger = new EndOfAttackTrigger(turnPlayer, TriggerCause.GAME_ACTION, this);
        triggerSystem.post(trigger);
        game.continuousTiming();
        game.interruptTiming();
        game.checkTiming();
    }

}

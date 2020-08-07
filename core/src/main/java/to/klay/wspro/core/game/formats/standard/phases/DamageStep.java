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
import to.klay.wspro.core.game.actions.AttackType;
import to.klay.wspro.core.game.actions.Combat;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.commands.Commands;
import to.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.EndOfAttackTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;

import java.util.Optional;

public class DamageStep extends BasePhase  {

    Logger log = LogManager.getLogger();

    public DamageStep(PhaseHandler phaseHandler, GamePlayer turnPlayer) {
        super(phaseHandler, turnPlayer, TurnPhase.DAMAGE_STEP);
    }

    @Override
    public void startPhase() {
        super.startPhase();

        Combat combat = phaseHandler.getCombat();
        Optional<PlayingCard> attackingCharacter = combat.getAttackingCharacter();
        if (attackingCharacter.isPresent()){
            int soul = attackingCharacter.get().getSoul();
            Commands.dealDamage(attackingCharacter.get(), soul,
                    phaseHandler.getNonTurnPlayer(), TriggerCause.GAME_ACTION, this);
        }
        game.checkTiming();

        if (combat.getAttackType() == AttackType.FRONTAL){
            phaseHandler.setNextPhase(TurnPhase.BATTLE_STEP);
        } else {
            //declare end of attack, return to declaration step
            BaseTrigger trigger = new EndOfAttackTrigger(turnPlayer, TriggerCause.GAME_ACTION, this);
            triggerSystem.post(trigger);
            game.continuousTiming();
            game.interruptTiming();
            game.checkTiming();
            phaseHandler.setNextPhase(TurnPhase.ATTACK_DECLARATION_STEP);
        }
    }
}

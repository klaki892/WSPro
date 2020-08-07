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

package to.klay.wspro.core.game.actions;

import com.google.common.eventbus.EventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.commands.Commands;
import to.klay.wspro.core.game.formats.standard.phases.TurnPhase;
import to.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.CardMovedTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.PhaseStartedTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.listeners.TriggerableAbilityListener;

import java.util.Optional;

/**
 * Represents the altercation between two cards during battle
 */
public class Combat {

    private static final Logger log = LogManager.getLogger();
    private final AttackType attackType;
    private  PlayingCard attackingCharacter;
    private  PlayingCard defendingCharacter;

    public Combat(AttackType attackType, PlayingCard attackingCharacter) {
        this.attackType = attackType;
        this.attackingCharacter = attackingCharacter;
        this.defendingCharacter = Commands.Utilities.getFacingCard(attackingCharacter).orElse(null);
    }

    public void setBattleStates(){

        //check for movement of attacking character and defending character (if exists)
        //if movement, remove state of attacked or defending
        TriggerableAbilityListener combatListener = new TriggerableAbilityListener() {
            EventBus eventBus;
            @Override
            public void triggerReceived(BaseTrigger trigger) {
                //if card moved
                if (trigger instanceof CardMovedTrigger) {
                    CardMovedTrigger movedTrigger = (CardMovedTrigger) trigger;

                    PlayingCard movedCard = movedTrigger.getSourceCard();

                    if (attackingCharacter != null && movedCard == attackingCharacter){
                        attackingCharacter = null;
                    }
                    if (defendingCharacter != null & movedCard == defendingCharacter){
                        defendingCharacter = null;
                    }
                }
                //remove this combat listener when combat is over
                if (trigger instanceof PhaseStartedTrigger){
                    PhaseStartedTrigger phaseStartedTrigger = (PhaseStartedTrigger) trigger;
                    if (phaseStartedTrigger.getPhase() == TurnPhase.ATTACK_DECLARATION_STEP)
                        deregister();
                }
            }

            @Override
            public void register(EventBus eventBus) {
                eventBus.register(this);
                this.eventBus = eventBus;
            }

            @Override
            public void deregister() {
                eventBus.unregister(this);
            }
        };

        combatListener.register(attackingCharacter.getGame().getTriggerManager());
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public Optional<PlayingCard> getAttackingCharacter() {
        return Optional.ofNullable(attackingCharacter);
    }

    public void setAttackingCharacter(PlayingCard attackingCard) {
        this.attackingCharacter = attackingCard;
    }

    public Optional<PlayingCard> getDefendingCharacter() {
        return Optional.ofNullable(defendingCharacter);
    }

    public void setDefendingCharacter(PlayingCard defendingCharacter) {
        this.defendingCharacter = defendingCharacter;
    }
}

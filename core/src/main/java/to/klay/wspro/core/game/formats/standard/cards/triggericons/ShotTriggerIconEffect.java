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

package to.klay.wspro.core.game.formats.standard.cards.triggericons;

import com.google.common.eventbus.EventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.abilities.components.effects.Effect;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.commands.Commands;
import to.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.DamageProcessedTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import to.klay.wspro.core.game.formats.standard.triggers.listeners.TriggerableAbilityListener;

public class ShotTriggerIconEffect implements Effect {

    private static final Logger log = LogManager.getLogger();
    private final PlayingCard triggerCard;
    private final PlayingCard attackingCard;

    public ShotTriggerIconEffect(PlayingCard triggerCard, PlayingCard attackingCard) {

        this.triggerCard = triggerCard;
        this.attackingCard = attackingCard;
    }

    @Override
    public void execute(Object... vars) {
        ShotTriggerIconEffect thisEffect = this;

        TriggerableAbilityListener shotAbilityListener = new TriggerableAbilityListener() {

            EventBus bus;
            final int effectTurn = attackingCard.getGame().getCurrentTurnNumber();

            @Override
            public void triggerReceived(BaseTrigger trigger) {

                //only happen during the turn this effect was made
                if (attackingCard.getGame().getCurrentTurnNumber() != effectTurn) {
                    deregister();
                    return;
                }

                if (trigger instanceof DamageProcessedTrigger) {
                    DamageProcessedTrigger dmgTrigger = (DamageProcessedTrigger) trigger;
                    if (dmgTrigger.isDamageCancelled()) {
                        Commands.dealDamage(attackingCard, 1, dmgTrigger.getReceivingPlayer(),
                                TriggerCause.GAME_ACTION, thisEffect);
                        //remove this effect so it doesnt happen for any future damage
                        this.deregister();
                    }
                }
            }

            @Override
            public void register(EventBus eventBus) {
                bus = eventBus;
                eventBus.register(this);
            }

            @Override
            public void deregister() {
                bus.unregister(this);
            }
        };

        attackingCard.addTriggerableAbility(shotAbilityListener);
        //todo announce effect?
    }

    @Override
    public GamePlayer getMaster() {
        return triggerCard.getMaster();
    }
}

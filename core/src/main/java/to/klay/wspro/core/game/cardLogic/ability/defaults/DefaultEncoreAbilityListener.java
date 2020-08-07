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

package to.klay.wspro.core.game.cardLogic.ability.defaults;

import com.google.common.eventbus.EventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.game.field.Zones;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.CardMovedTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.listeners.TriggerableAbilityListener;

public class DefaultEncoreAbilityListener implements TriggerableAbilityListener {

    private static final Logger log = LogManager.getLogger();
    private final PlayingCard card;
    private EventBus eventBus;

    public DefaultEncoreAbilityListener(PlayingCard card ) {
        this.card = card;
    }

    @Override
    public void triggerReceived(BaseTrigger trigger) {
        if (trigger instanceof CardMovedTrigger){
            CardMovedTrigger movedTrigger = (CardMovedTrigger) trigger;
            if ((movedTrigger.getDestinationCard() == this.card) &&
                    (Zones.isOnStage(movedTrigger.getSourceZone())) &&
                    (movedTrigger.getDestinationZone().getZoneName() == Zones.ZONE_WAITING_ROOM)){
                card.getGame().getTimingManager().add(new DefaultEncoreAbility(card, movedTrigger));
            }
        }
    }

    @Override
    public void register(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.register(this);
    }

    @Override
    public void deregister() {
        eventBus.unregister(this);
    }
}

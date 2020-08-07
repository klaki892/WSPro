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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.CardOrientation;
import to.klay.wspro.core.api.cards.abilities.components.effects.Effect;
import to.klay.wspro.core.api.game.field.Zones;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.commands.Commands;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import to.klay.wspro.core.game.formats.standard.zones.PlayZone;

public class TreasureTriggerIconEffect implements Effect {

    private static final Logger log = LogManager.getLogger();
    private final PlayingCard triggerCard;

    public TreasureTriggerIconEffect(PlayingCard triggerCard) {

        this.triggerCard = triggerCard;
    }

    @Override
    public void execute(Object... vars) {
        GamePlayer player = getMaster();
        PlayZone deck = player.getPlayArea().getPlayZone(Zones.ZONE_DECK);
        PlayZone stock = player.getPlayArea().getPlayZone(Zones.ZONE_STOCK);
        PlayZone resolution = player.getPlayArea().getPlayZone(Zones.ZONE_RESOLUTION);
        PlayZone hand = player.getPlayArea().getPlayZone(Zones.ZONE_HAND);

        //move this card to player hand
        Commands.moveCard(triggerCard, resolution, hand, Commands.Utilities.getTopOfZoneIndex(hand),
                CardOrientation.STAND, hand.getVisibility(), TriggerCause.GAME_ACTION, this);


        //confirm they want to do effect
        boolean doAction = Commands.Utilities.getConfirmationFromPlayer(player);
        if (doAction){
            PlayingCard topOfZoneCard = Commands.Utilities.getTopOfZoneCards(deck, 1).get(0);
            Commands.moveCard(topOfZoneCard, deck, stock, Commands.Utilities.getTopOfZoneIndex(stock),
                    CardOrientation.REST, stock.getVisibility(), TriggerCause.GAME_ACTION, this);
        }

        //todo announce effect?
    }

    @Override
    public GamePlayer getMaster() {
        return triggerCard.getMaster();
    }
}

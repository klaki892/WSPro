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
import to.klay.wspro.core.api.game.field.Zones;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.actions.PlayChoice;
import to.klay.wspro.core.game.actions.PlayChoiceAction;
import to.klay.wspro.core.game.actions.PlayChoiceType;
import to.klay.wspro.core.game.cards.CardType;
import to.klay.wspro.core.game.cards.filters.CardFilter;
import to.klay.wspro.core.game.cards.filters.ColorFilter;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.commands.Commands;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import to.klay.wspro.core.game.formats.standard.zones.PlayZone;

import java.util.List;
import java.util.stream.Collectors;

public class ClimaxPhase extends BasePhase  {

    Logger log = LogManager.getLogger();

    public ClimaxPhase(PhaseHandler phaseHandler, GamePlayer turnPlayer) {
        super(phaseHandler, turnPlayer, TurnPhase.CLIMAX_PHASE);
    }

    @Override
    public void startPhase() {
        super.startPhase();
        PlayZone hand = turnPlayer.getPlayArea().getPlayZone(Zones.ZONE_HAND);
        PlayZone climaxZone = turnPlayer.getPlayArea().getPlayZone(Zones.ZONE_CLIMAX);

        List<PlayingCard> handCards = hand.getContents();

        List<PlayingCard> climaxCards = handCards.stream()
                .filter(card -> card.getCardType() == CardType.CLIMAX).collect(Collectors.toList());

        //get playable colors requirement
        PlayZone clock = turnPlayer.getPlayArea().getPlayZone(Zones.ZONE_CLOCK);
        PlayZone level = turnPlayer.getPlayArea().getPlayZone(Zones.ZONE_LEVEL);
        CardFilter colorFilter = ColorFilter.getPlayableColors(clock, level);

        List<PlayingCard> playableClimaxes = colorFilter.filter(climaxCards);

        game.checkTiming();

        //ask the player to choose a climax or not
        List<PlayChoice> choices = playableClimaxes.stream().map(PlayChoice::makeCardChoice).collect(Collectors.toList());
        choices.add(PlayChoice.makeActionChoice(PlayChoiceAction.END_ACTION));

        PlayChoice choice = Commands.makeSinglePlayChoice(turnPlayer, choices);

        if (choice.getChoiceType() == PlayChoiceType.CHOOSE_CARD) {
            PlayingCard climaxCard = choice.getCard();
            Commands.playCard(turnPlayer, climaxCard, hand, climaxZone, TriggerCause.GAME_ACTION, this);
        }
        game.checkTiming();
    }

}

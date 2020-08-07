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
import to.klay.wspro.core.api.cards.CardOrientation;
import to.klay.wspro.core.api.game.field.Zones;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.actions.PlayChoice;
import to.klay.wspro.core.game.actions.PlayChoiceAction;
import to.klay.wspro.core.game.actions.PlayChoiceType;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.commands.Commands;
import to.klay.wspro.core.game.formats.standard.triggers.CardClockedTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import to.klay.wspro.core.game.formats.standard.zones.PlayZone;

import java.util.List;
import java.util.stream.Collectors;

public class ClockPhase extends BasePhase  {

    Logger log = LogManager.getLogger();

    public ClockPhase(PhaseHandler phaseHandler, GamePlayer turnPlayer) {
        super(phaseHandler, turnPlayer, TurnPhase.CLOCK_PHASE);
    }

    @Override
    public void startPhase() {
        super.startPhase();

        PlayZone clock = turnPlayer.getPlayArea().getPlayZone(Zones.ZONE_CLOCK);
        PlayZone hand = turnPlayer.getPlayArea().getPlayZone(Zones.ZONE_HAND);
        List<PlayingCard> handContents = hand.getContents();

        //create the player choice
        List<PlayChoice> choices = handContents.stream().map(PlayChoice::makeCardChoice).collect(Collectors.toList());

        //Player doesnt have to clock
        choices.add(PlayChoice.makeActionChoice(PlayChoiceAction.END_ACTION));

        PlayChoice choice = Commands.makeSinglePlayChoice(turnPlayer, choices);

        if (choice.getChoiceType() == PlayChoiceType.CHOOSE_CARD){
            PlayingCard cardToClock = choice.getCard();

            Commands.moveCard(cardToClock, hand, clock, Commands.Utilities.getTopOfZoneIndex(clock),
                    CardOrientation.STAND, clock.getVisibility(), TriggerCause.GAME_ACTION, this);

            //issue event
            CardClockedTrigger trigger = new CardClockedTrigger(turnPlayer, cardToClock, TriggerCause.GAME_ACTION, this);
            game.getTriggerManager().post(trigger);
            game.continuousTiming();

            Commands.drawCard(turnPlayer, TriggerCause.GAME_ACTION, this);
            Commands.drawCard(turnPlayer, TriggerCause.GAME_ACTION, this);
        }

        game.checkTiming();
    }

}

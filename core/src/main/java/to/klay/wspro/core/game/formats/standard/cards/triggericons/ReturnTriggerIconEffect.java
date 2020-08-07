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
import to.klay.wspro.core.game.actions.PlayChoice;
import to.klay.wspro.core.game.actions.PlayChoiceAction;
import to.klay.wspro.core.game.actions.PlayChoiceType;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.commands.Commands;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import to.klay.wspro.core.game.formats.standard.zones.PlayZone;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ReturnTriggerIconEffect implements Effect {

    private static final Logger log = LogManager.getLogger();
    private final PlayingCard triggerCard;

    public ReturnTriggerIconEffect(PlayingCard triggerCard) {

        this.triggerCard = triggerCard;
    }

    @Override
    public void execute(Object... vars) {
        GamePlayer player = getMaster();
        GamePlayer opponent = player.getGame().getNonTurnPlayer();
        PlayZone opponentHand = opponent.getPlayArea().getPlayZone(Zones.ZONE_HAND);
        Collection<PlayZone> opponentStage = opponent.getPlayArea().getPlayZones(Zones.ZONE_STAGE);

        //get all opponent cards on stage
        List<PlayChoice> choices = new  ArrayList<>();
        for (PlayZone stageZone : opponentStage) {
            for (PlayingCard card : stageZone.getContents()) {
                choices.add(PlayChoice.makeCardChoice(card));
            }
        }
        choices.add(PlayChoice.makeActionChoice(PlayChoiceAction.END_ACTION));

        PlayChoice choice = Commands.makeSinglePlayChoice(player, choices);
        if (choice.getChoiceType() != PlayChoiceType.CHOOSE_ACTION){
            //bounce the opponents card back to hand
            PlayingCard chosenCard = choice.getCard();
            Commands.moveCard(chosenCard, Commands.Utilities.getCardOnStage(chosenCard).get(),
                    opponentHand, Commands.Utilities.getTopOfZoneIndex(opponentHand), CardOrientation.STAND,
                    opponentHand.getVisibility(), TriggerCause.GAME_ACTION, this);
        }
        //todo announce return effect?
    }

    @Override
    public GamePlayer getMaster() {
        return triggerCard.getMaster();
    }
}

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
import to.klay.wspro.core.api.cards.CardIcon;
import to.klay.wspro.core.api.cards.abilities.AbilityKeyword;
import to.klay.wspro.core.api.game.field.Zones;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.actions.PlayChoice;
import to.klay.wspro.core.game.actions.PlayChoiceAction;
import to.klay.wspro.core.game.actions.PlayChoiceType;
import to.klay.wspro.core.game.cardLogic.ability.ActivatedAbility;
import to.klay.wspro.core.game.cards.CardType;
import to.klay.wspro.core.game.cards.filters.CardFilter;
import to.klay.wspro.core.game.cards.filters.CardIconFilter;
import to.klay.wspro.core.game.cards.filters.CardTypeFilter;
import to.klay.wspro.core.game.cards.filters.LevelFilter;
import to.klay.wspro.core.game.cards.filters.NumericFilter;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.commands.Commands;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import to.klay.wspro.core.game.formats.standard.zones.PlayZone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CounterStep extends BasePhase  {

    Logger log = LogManager.getLogger();

    public CounterStep(PhaseHandler phaseHandler, GamePlayer turnPlayer) {
        super(phaseHandler, turnPlayer, TurnPhase.COUNTER_STEP);
    }

    @Override
    public void startPhase() {
        super.startPhase();
        game.checkTiming();
        GamePlayer opponent = phaseHandler.getNonTurnPlayer();
        phaseHandler.setNextPhase(TurnPhase.DAMAGE_STEP);

        PlayZone hand = opponent.getPlayArea().getPlayZone(Zones.ZONE_HAND);
        PlayZone resolution = opponent.getPlayArea().getPlayZone(Zones.ZONE_RESOLUTION);
        List<PlayingCard> handCards = hand.getContents();

        List<PlayingCard> eventCounters = getPlayableEvents(handCards);
        ArrayList<ActivatedAbility> playableAbilities = getPlayableCounterAbilities(handCards);


        //add all playable counters
        List<PlayChoice> choices = new ArrayList<>();
        choices.addAll(playableAbilities.stream().map(PlayChoice::makeAbilityChoice).collect(Collectors.toList()));
        choices.addAll(eventCounters.stream().map(PlayChoice::makeCardChoice).collect(Collectors.toList()));

        choices.add(PlayChoice.makeActionChoice(PlayChoiceAction.END_ACTION));

        //ask the player to choose
        PlayChoice choice = Commands.makeSinglePlayChoice(opponent, choices);

        if (choice.getChoiceType() == PlayChoiceType.CHOOSE_ABILITY){
            choice.getAbility().performEffect();
        }
        if (choice.getChoiceType() == PlayChoiceType.CHOOSE_CARD){
            //play event card
            Commands.playCard(opponent, choice.getCard(), hand, resolution, TriggerCause.GAME_ACTION, this);
        }

        game.checkTiming();
    }

    private ArrayList<ActivatedAbility> getPlayableCounterAbilities(List<PlayingCard> handCards) {
        //filter Character Abilities (in hand) for Counter keyword and make sure they're payable
        CardFilter characterFilter = new CardTypeFilter(CardType.CHARACTER);
        ArrayList<ActivatedAbility> playableAbilities = new ArrayList<>();

        for (PlayingCard handCard : characterFilter.filter(handCards)) {
            for (ActivatedAbility ability : handCard.getActivatableAbilities()) {
                if (ability.getKeywords().contains(AbilityKeyword.KEYWORD_BACKUP) ||
                        ability.getKeywords().contains(AbilityKeyword.KEYWORD_COUNTER)){
                    if (ability.getCost().isPayable()) {
                        playableAbilities.add(ability);
                    }
                }
            }
        }
        return playableAbilities;
    }

    private List<PlayingCard> getPlayableEvents(List<PlayingCard> handCards) {
        PlayZone level = phaseHandler.getNonTurnPlayer().getPlayArea().getPlayZone(Zones.ZONE_LEVEL);

        //filter event cards in hand for counter icon and that their cost can be paid
        CardFilter levelFilter = new LevelFilter(NumericFilter.Operation.LESS_THAN_OR_EQUAL, level.size());
        CardFilter counterIconFilter = new CardIconFilter(CardIcon.COUNTER);
        CardFilter eventFilter = new CardTypeFilter(CardType.EVENT);

        return CardFilter.andFilter(
                Arrays.asList(levelFilter, counterIconFilter, eventFilter))
                .filter(handCards).stream()
                .filter(event -> event.getCostActions().isPayable()).collect(Collectors.toList());
    }

}

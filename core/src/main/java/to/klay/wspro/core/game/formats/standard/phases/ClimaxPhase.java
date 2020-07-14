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

package ton.klay.wspro.core.game.formats.standard.phases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.CardOrientation;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.scripting.cards.CardType;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.commands.Commands;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerCause;

import java.util.List;
import java.util.Optional;
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
        PlayZone climaxZone = turnPlayer.getPlayArea().getPlayZone(Zones.ZONE_HAND);

        List<PlayingCard> handCards = hand.getContents();

        List<PlayingCard> climaxCards = handCards.stream()
                .filter(card -> card.getCardType() == CardType.CLIMAX).collect(Collectors.toList());


        game.checkTiming();
        Optional<PlayingCard> climaxCard = turnPlayer.getController().chooseClimaxPhaseCard(climaxCards);
        climaxCard.ifPresent(card -> Commands.moveCard(card, hand, climaxZone,
                Commands.Utilities.getTopOfZoneIndex(climaxZone), CardOrientation.STAND,
                climaxZone.getVisibility(), TriggerCause.GAME_ACTION, this));

        //FIXME: Make a Playtiming builder (canselect, from zones, etc) and properly filter for cards and issue corresponding events
        game.checkTiming();
    }

}

package ton.klay.wspro.core.game.formats.standard.cards.triggericons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.CardOrientation;
import ton.klay.wspro.core.api.cards.abilities.components.effects.Effect;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.actions.PlayChoice;
import ton.klay.wspro.core.game.actions.PlayChoiceAction;
import ton.klay.wspro.core.game.actions.PlayChoiceType;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.commands.Commands;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import ton.klay.wspro.core.game.formats.standard.zones.PlayZone;

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

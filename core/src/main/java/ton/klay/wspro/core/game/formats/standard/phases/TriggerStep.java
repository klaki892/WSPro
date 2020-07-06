package ton.klay.wspro.core.game.formats.standard.phases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.CardOrientation;
import ton.klay.wspro.core.api.cards.CardTrigger;
import ton.klay.wspro.core.api.cards.GameVisibility;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.actions.AttackType;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.commands.Commands;
import ton.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerCheckedTrigger;

import java.util.Collection;

public class TriggerStep extends BasePhase  {

    Logger log = LogManager.getLogger();

    public TriggerStep(PhaseHandler phaseHandler, GamePlayer turnPlayer) {
        super(phaseHandler, turnPlayer, TurnPhase.TRIGGER_STEP);
    }

    @Override
    public void startPhase() {
        super.startPhase();

        if (phaseHandler.getCombat().getAttackType() == AttackType.FRONTAL){
            phaseHandler.setNextPhase(TurnPhase.COUNTER_STEP);
        } else {
            phaseHandler.setNextPhase(TurnPhase.DAMAGE_STEP);
        }

        PlayZone deck = turnPlayer.getPlayArea().getPlayZone(Zones.ZONE_DECK);
        PlayZone resolution = turnPlayer.getPlayArea().getPlayZone(Zones.ZONE_RESOLUTION);
        PlayZone stock = turnPlayer.getPlayArea().getPlayZone(Zones.ZONE_STOCK);


        //trigger check
        PlayingCard triggerCard = Commands.Utilities.getTopOfZoneCards(deck, 1).get(0);
        PlayingCard triggerCardInReso = Commands.moveCard(triggerCard, deck, resolution, Commands.Utilities.getTopOfZoneIndex(resolution),
                CardOrientation.STAND, GameVisibility.VISIBLE_TO_ALL, TriggerCause.GAME_ACTION, this).getDestinationCard();

        Collection<CardTrigger> triggers = triggerCard.getTriggerIcons();
        //todo resolve trigger icons
        BaseTrigger trigger = new TriggerCheckedTrigger(triggerCardInReso, TriggerCause.GAME_ACTION, this);
        triggerSystem.post(trigger);
        game.continuousTiming();
        game.interruptTiming();
        game.checkTiming();


        //put in stock
        Commands.moveCard(triggerCardInReso, resolution, stock, Commands.Utilities.getTopOfZoneIndex(stock),
                CardOrientation.REST, GameVisibility.HIDDEN, TriggerCause.GAME_ACTION, this);

        game.checkTiming();
    }

}

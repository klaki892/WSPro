package ton.klay.wspro.core.game.formats.standard.phases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.CardOrientation;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.formats.standard.commands.Commands;
import ton.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import ton.klay.wspro.core.game.formats.standard.triggers.TurnStartedTrigger;

public class StandPhase extends BasePhase  {

    Logger log = LogManager.getLogger();

    public StandPhase(PhaseHandler phaseHandler, GamePlayer turnPlayer) {
        super(phaseHandler, turnPlayer, TurnPhase.STAND_PHASE);
    }

    @Override
    public void beforePhase() {
        //announce start of turn
        BaseTrigger trigger = new TurnStartedTrigger(turnPlayer, TriggerCause.GAME_ACTION, this);
        game.getTriggerManager().post(trigger);
        game.continuousTiming();
        game.incrementTurnCount();

        super.beforePhase();
    }

    @Override
    public void startPhase() {
        super.startPhase();

        for (PlayZone stagePosition : turnPlayer.getPlayArea().getPlayZones(Zones.ZONE_CENTER_STAGE)) {
            stagePosition.getContents().forEach(card -> {
                Commands.changeCardOrientation(card, CardOrientation.STAND, TriggerCause.GAME_ACTION, this);
            });
        }
        game.checkTiming();
    }

}

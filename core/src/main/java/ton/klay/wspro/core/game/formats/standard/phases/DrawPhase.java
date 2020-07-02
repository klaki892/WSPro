package ton.klay.wspro.core.game.formats.standard.phases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.formats.standard.commands.Commands;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerCause;

public class DrawPhase extends BasePhase  {

    Logger log = LogManager.getLogger();

    public DrawPhase(PhaseHandler phaseHandler, GamePlayer turnPlayer) {
        super(phaseHandler, turnPlayer, TurnPhase.DRAW_PHASE);
    }

    @Override
    public void startPhase() {
        super.startPhase();
        Commands.drawCard(turnPlayer, TriggerCause.GAME_ACTION, this);
        game.checkTiming();
    }

}

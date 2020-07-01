package ton.klay.wspro.core.game.formats.standard.phases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.player.GamePlayer;

public class StandPhase extends BasePhase  {

    Logger log = LogManager.getLogger();

    public StandPhase(PhaseHandler phaseHandler, GamePlayer turnPlayer) {
        super(phaseHandler, turnPlayer, TurnPhase.STAND_PHASE);
    }

    @Override
    public void beforePhase() {
        //TODO: FIRE TURN START
        game.incrementTurnCount();
        super.beforePhase();
    }

    @Override
    public void startPhase() {
        super.startPhase();

        /* TODO
            4.	Stand all cards that can be stood automatically
                a.	for each stand, trigger check on stand ability
         */
        game.checkTiming();
    }

}

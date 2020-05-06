package ton.klay.wspro.core.game.formats.standard.phases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.LoseConditions;
import ton.klay.wspro.core.api.game.communication.Communicable;
import ton.klay.wspro.core.api.game.phase.EndPhaseForcefully;
import ton.klay.wspro.core.api.game.phase.GamePhase;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.game.Duel;
import ton.klay.wspro.core.api.cards.abilities.components.AbilityConditions;
import ton.klay.wspro.core.game.commands.CommandUtil;
import ton.klay.wspro.core.game.commands.lowlevel.GameEndCommand;

public class StandPhase implements GamePhase {

    Logger log = LogManager.getLogger();

    public StandPhase() { }

//    /**
//     * Preform the Stand Phase
//     */
//    @Override
//    public void startPhase() {
//        //todo add logs to stand phase
//        log.debug("Starting Stand Phase");
//        duel.triggerCheck(AbilityConditions.CONDITION_START_STAND_PHASE);
//        duel.checkTiming();
//        //todo: Action: Stand all Cards on the stage
//        duel.triggerCheck(AbilityConditions.CONDITION_ON_STAND);
//        duel.checkTiming();
//
//    }
//
//    @Override
//    public GamePhase nextPhase() {
//        //todo
//        return null;
//    }

    @Override
    public void startPhase(GameContext context, GamePlayer owner) throws EndPhaseForcefully {
        //todo

        //TODO REMOVE ME
        Communicable cmd = CommandUtil.wrapCommunicable(
                GameEndCommand.CMD_NAME, LoseConditions.DRAW.name()
        );
        context.getGameCommunicationManager().decode(cmd, context.getCommandSender());
    }

    @Override
    public String getPhaseName() {
        return TurnPhase.TURN_PHASE_STAND.name();
    }
}

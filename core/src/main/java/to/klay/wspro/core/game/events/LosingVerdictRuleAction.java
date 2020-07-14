package to.klay.wspro.core.game.events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.game.LoseConditions;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.formats.standard.commands.Commands;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;

public class LosingVerdictRuleAction implements RuleAction {

    private static final Logger log = LogManager.getLogger();
    private final GamePlayer player;
    private final LoseConditions loseReason;

    public LosingVerdictRuleAction(GamePlayer player, LoseConditions loseReason){

        this.player = player;
        this.loseReason = loseReason;
    }

    @Override
    public void execute() {
        Commands.issuePlayerLost(player, loseReason, TriggerCause.GAME_ACTION, player);
    }

    public GamePlayer getPlayer() {
        return player;
    }

    public LoseConditions getLoseReason() {
        return loseReason;
    }
}

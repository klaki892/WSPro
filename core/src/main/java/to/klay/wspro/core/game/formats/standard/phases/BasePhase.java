package to.klay.wspro.core.game.formats.standard.phases;

import com.google.common.eventbus.EventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.game.phase.GamePhase;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.Game;
import to.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.PhaseStartedTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;

public abstract class BasePhase implements GamePhase {

    private static final Logger log = LogManager.getLogger();
    private final TurnPhase phaseIdentifier;

    protected final PhaseHandler phaseHandler;
    protected final GamePlayer turnPlayer;
    protected final Game game;
    protected final EventBus triggerSystem;


    public BasePhase(PhaseHandler phaseHandler, GamePlayer turnPlayer, TurnPhase phaseIdentifier) {
        this.phaseHandler = phaseHandler;
        this.turnPlayer = turnPlayer;
        this.phaseIdentifier = phaseIdentifier;
        game = this.phaseHandler.getGame();
        triggerSystem = game.getTriggerManager();

    }

    /**
     * Declares the start of the phase and performs the corresponding {@link Game#checkTiming()}
     */
    protected void beforePhase() {
        BaseTrigger trigger = new PhaseStartedTrigger(turnPlayer, getIdentifier(), TriggerCause.GAME_ACTION, this);
        triggerSystem.post(trigger);
        game.continuousTiming();
        game.interruptTiming();
        game.checkTiming();
    }

    /**
     * Starts executing the instructions for this phase.
     * <br/> Calls {@link #beforePhase()} before any executed actions
     */
    @Override
    public  void startPhase() {
        beforePhase();
    }

    @Override
    public TurnPhase getIdentifier() {
        return phaseIdentifier;
    }

    @Override
    public GamePlayer getMaster() {
        return turnPlayer;
    }
}

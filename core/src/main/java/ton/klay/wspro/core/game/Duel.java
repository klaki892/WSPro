package ton.klay.wspro.core.game;

import com.google.common.eventbus.EventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.abilities.components.AbilityConditions;
import ton.klay.wspro.core.api.game.GameRuntimeException;
import ton.klay.wspro.core.api.game.GameStatus;
import ton.klay.wspro.core.game.formats.standard.phases.PhaseHandler;
import ton.klay.wspro.core.game.formats.standard.phases.TurnPhase;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.scripting.ScriptEngine;
import ton.klay.wspro.core.game.enums.PlayTiming;
import ton.klay.wspro.core.game.events.InterruptRuleAction;
import ton.klay.wspro.core.game.formats.standard.zones.*;
import ton.klay.wspro.core.game.scripting.ScriptingFunctions;
import ton.klay.wspro.core.game.throwables.LoseCondition;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Random;

public class Duel  {

    private static final Logger log = LogManager.getLogger();

    public GamePlayer player1, player2;
    public GamePlayer currentTurnPlayer;
    public GamePlayer opposingPlayer;
    private ScriptEngine scriptEngine;
    private ScriptingFunctions scriptingFunctions;
    private EventBus TriggerManager;
    private PhaseHandler phaseHandler;

    TurnPhase currentPhase;
    private GameStatus gameStatus;
    private Random random;

    public Duel(GamePlayer player1, GamePlayer player2){
        this.player1 = player1;
        this.player2 = player2;
        setup();
    }


    /////////////game setup and running methods

    private void setup(){

        initializePlayingField();

        //todo obtain decks
        //todo check deck restrictions

        //start scripting engine
        scriptingFunctions = new ScriptingFunctions(this);
//        scriptEngine = new LuaScriptEngine();

        TriggerManager = new EventBus("Game Trigger Manager");
        phaseHandler = new PhaseHandler(this);
        pregame();
    }


    /**
    *              -begin game setup interactions *(overridable method)<br>
        - throwEx decks, place on deck zone
        - randomly determine who goes first (*overridable method)
        - draw 5 and mill, redraw up to 5

     */
    private void pregame() {
        //todo pregame setup as listed in docs

    }

    /**
     * Starts the duel with a specified player
     * @param startingPlayer player to take first turn
     */
    public void startGame(GamePlayer startingPlayer){
        try {
            startTurn(startingPlayer);
        } catch (LoseCondition loseCondition)
        {
            endGame(loseCondition);
        }
    }

    /**
     * Starts the duel with a randomly chosen player going first
     */
    public void startGame(){
        startGame(currentTurnPlayer); //this works because pregame was suppose to set currentTurnPlayer.
    }

    private void startTurn(GamePlayer player) throws LoseCondition {
        currentTurnPlayer = player;
        opposingPlayer = (currentTurnPlayer == player1) ? player2 : player1;

        try {
            //go through the phases
//            currentPhase = TurnPhase.TURN_PHASE_STAND;
//            new StandPhase(this).startPhase();
//
//            currentPhase = TurnPhase.TURN_PHASE_DRAW;
//            new DrawPhase(this).startPhase();
//
//            currentPhase = TurnPhase.TURN_PHASE_CLOCK;
//            new ClockPhase(this).startPhase();
//
//            currentPhase = TurnPhase.TURN_PHASE_MAIN;
//            new MainPhase(this).startPhase();
//
//            currentPhase = TurnPhase.TURN_PHASE_ATTACK;
//            new AttackPhase(this).startPhase();
//
//            currentPhase = TurnPhase.TURN_PHASE_END;
//            new EndPhase(this).startPhase();
        } catch (GameRuntimeException ex){
            unexpectedEndGame(ex);
        }
        //start turn of other player
        startTurn(opposingPlayer);
    }

    public void unexpectedEndGame(GameRuntimeException ex){
        log.fatal("Stopping Game " + this + " Due to exception: " + ex.getMessage());
        //todo code in unexpected endGame functionality, no winner, preform log dump & cleanup (if any)
    }

    public void endGame(LoseCondition loseCondition){
        //todo code in endGame functionality
    }

    private void initializePlayingField() {
        for (GamePlayer player : new GamePlayer[]{player1, player2}){
            player.setPlayArea(new StandardWeissPlayArea(player));
        }
    }


    ///////////////Gameplay related general functions


    //@Override
    public void checkTiming(){
        //TODO
    }


    ////////////////////Helper functions for scripts and whatnot


    //@Override
    public TurnPhase getCurrentPhase() {
        return currentPhase;
    }

    //@Override
    public GamePlayer getCurrentTurnPlayer() {
        return currentTurnPlayer;
    }


    //@Override
    public ScriptingFunctions getScriptingFunctions() {
        return scriptingFunctions;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public GameStatus getGameState() {
        return gameStatus;
    }

    public boolean isGameOver() {
        return getGameState() == GameStatus.FINISHED;
    }

    public EventBus getTriggerManager() {
        return TriggerManager;
    }

    public int getTurnCount() {
        return phaseHandler.getTurnNumber();
    }

    public void incrementTurnCount(){
        phaseHandler.setTurnNumber(getTurnCount()+1);
    }

    public Random getRandom() {
        if (random != null)
            return random;
        else {
            //use a SecureRandom to generate entropy, then use a deterministic method for the rest of the game for repeatability
            long seed = ByteBuffer.wrap(new SecureRandom().generateSeed(8)).getLong();
            log.info("Random Seed Generated: " + seed);
            return random = new Random(seed);
        }
    }
}

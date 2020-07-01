package ton.klay.wspro.core.game;

import com.google.common.eventbus.EventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.GameRuntimeException;
import ton.klay.wspro.core.api.game.GameStatus;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.scripting.ScriptEngine;
import ton.klay.wspro.core.game.formats.standard.phases.PhaseHandler;
import ton.klay.wspro.core.game.formats.standard.phases.TurnPhase;
import ton.klay.wspro.core.game.formats.standard.zones.StandardWeissPlayArea;
import ton.klay.wspro.core.game.scripting.ScriptingFunctions;
import ton.klay.wspro.core.game.throwables.LoseCondition;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Random;

public class Duel  {

    private static final Logger log = LogManager.getLogger();

    public GamePlayer player1, player2;
    private GamePlayer currentTurnPlayer;
    private GamePlayer opposingPlayer;
    private ScriptEngine scriptEngine;
    private ScriptingFunctions scriptingFunctions;
    private EventBus TriggerManager;
    private PhaseHandler phaseHandler;
    private TimingManager timingManager;

    TurnPhase currentPhase;
    private GameStatus gameStatus;
    private Random random;
    private int fundamentalOrderCounter = 0;

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
        timingManager = new TimingManager(this);
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




    ////////////////////Helper functions for scripts and whatnot

    //@Override

    public TurnPhase getCurrentPhase() {
        return currentPhase;
    }

    //@Override
    public GamePlayer getCurrentTurnPlayer() {
        return currentTurnPlayer;
    }

    public GamePlayer getNonTurnPlayer() {
        return opposingPlayer;
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

    public int getNextFundamentalOrder(){
        return ++fundamentalOrderCounter;
    }

    public void checkTiming(){
        timingManager.doCheckTiming();
    }

    /**
     * Checks the game state for Interrupt Type Rule Actions and executes them. <br/>
     * The Rule actions will <b>NOT</b> be executed if a Interrupt-Timing
     */
    public void interruptTiming() {
        timingManager.doInterruptTiming();
    }

    public void enableInterruptLock() {
        timingManager.enableInterruptLock();
    }

    /**
     * stops the freze on  Interrupt type rule actions and performs a {@link #interruptTiming()}
     */
    public void disableInterruptLock() {
        timingManager.disableInterruptLock();
    }

    public void continuousTiming() {
        //todo reset all cards
        /* TODO:
            continuous Effect
            If continious effect affects a specific ZONE instead of a card:
                the change is applied the moment it enters the zone.
                If the effect comes from an automatic ability, effects on zones apply before basic resolution.
            If card stats needed, basic resolve as follows:
                1. obtain base stats of card
                2. apply effects that DO NOT change the state of the card.
                3. apply effects that DO change the state of the card.
                    3a.Indepedent Effects are resolved in Fundamental Order*
                    3b.Dependent Effects* are resolved after Indepedent
                    *Dependent Effects (multiple state changes)
                        If Effect A applies a State that Effect B needs to be active:
                            Effect B is labeled a Dependent Effect and always occurs after Indepedent effects.
                            (A.K.A if an Effect requires a conditional to apply, its dependent)
                    *Fundamental Order (Multiple Indepedent state changes)
                        If a Continuous Effect comes from Continuous Ability:
                            When the card is placed on its current zone is the fundemental order of that card.
                            If character on stage owns the cont ability, when that character was placed on that specific stage position from another zone is it's fundamental order.
                            Else, when the ability is played is its fundamental order.
         */
    }
}

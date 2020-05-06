package ton.klay.wspro.core.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.abilities.components.AbilityConditions;
import ton.klay.wspro.core.api.game.GameRuntimeException;
import ton.klay.wspro.core.api.game.GameStatus;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.game.formats.standard.phases.TurnPhase;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.scripting.ScriptEngine;
import ton.klay.wspro.core.game.enums.PlayTiming;
import ton.klay.wspro.core.game.events.ConditionHandler;
import ton.klay.wspro.core.game.events.InterruptRuleAction;
import ton.klay.wspro.core.game.events.TimingEventHandler;
import ton.klay.wspro.core.game.formats.standard.zones.*;
import ton.klay.wspro.core.game.scripting.ScriptingFunctions;
import ton.klay.wspro.core.game.throwables.LoseCondition;

import java.util.ArrayList;

public class Duel  {

    private static final Logger log = LogManager.getLogger();

    public GamePlayer player1, player2;
    public GamePlayer currentTurnPlayer;
    public GamePlayer opposingPlayer;
    public int turnCount = 0;
    private TimingEventHandler timingHandler;
    private ConditionHandler conditionHandler;
    private ArrayList<PlayZone> playingField;
    private ScriptEngine scriptEngine;
    private ScriptingFunctions scriptingFunctions;

    TurnPhase currentPhase;

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



        timingHandler = new TimingEventHandler(this);
        conditionHandler = new ConditionHandler(timingHandler);

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
        turnCount++;
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
        GamePlayer[] players = {player1, player2};
        playingField = new ArrayList<>();

        for (GamePlayer player : players){

            //initialize individual zones
            playingField.add(new StockZone(player));
            playingField.add(new ClockZone(player));
            playingField.add(new LevelZone(player));
            playingField.add(new HandZone(player));
            playingField.add(new DeckZone(player));
            playingField.add(new WaitingRoomZone(player));
            playingField.add(new MemoryZone(player));
            playingField.add(new ResolutionZone(player));

            //initialize individual stage zones
            playingField.add(new BackStageZone(player, Zones.ZONE_BACK_STAGE_LEFT));
            playingField.add(new BackStageZone(player, Zones.ZONE_BACK_STAGE_RIGHT));

            playingField.add(new CenterStageZone(player, Zones.ZONE_CENTER_STAGE_RIGHT));
            playingField.add(new CenterStageZone(player, Zones.ZONE_CENTER_STAGE_LEFT));
            playingField.add(new CenterStageZone(player, Zones.ZONE_CENTER_STAGE_MIDDLE));

            playingField.add(new MarkerZone(player, Zones.ZONE_BACK_STAGE_LEFT_MARKER));
            playingField.add(new MarkerZone(player, Zones.ZONE_BACK_STAGE_RIGHT_MARKER));
            playingField.add(new MarkerZone(player, Zones.ZONE_CENTER_STAGE_LEFT_MARKER));
            playingField.add(new MarkerZone(player, Zones.ZONE_CENTER_STAGE_RIGHT_MARKER));
            playingField.add(new MarkerZone(player, Zones.ZONE_CENTER_STAGE_MIDDLE_MARKER));
        }
    }


    ///////////////Gameplay related general functions

    //@Override
    public void triggerCheck(AbilityConditions effectTrigger){
        conditionHandler.fireCondition(effectTrigger);
        log.debug("Event Check Fired for " + effectTrigger.toString());
    }

    //@Override
    public void checkTiming(){
        try {
            timingHandler.checkTiming();
        } catch (InterruptRuleAction ira){
            log.debug(ira.getMessage());
            log.trace(ira);
        }
    }

    //@Override
    public void playTiming(PlayTiming playTiming){
        try{
            timingHandler.playTiming(playTiming);
        } catch (InterruptRuleAction ira) {
            log.debug(ira.getMessage());
            log.trace(ira);
        }
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

    public ArrayList<ton.klay.wspro.core.api.game.field.PlayZone> getPlayingField() {
        return playingField;
    }

    //@Override
    public ScriptingFunctions getScriptingFunctions() {
        return scriptingFunctions;
    }

    public GameStatus getGameState() {
        //TODO
        return null;
    }
}

/*******************************************************************************
 *  WSPro - Trading Card Game Simulator
 *  Copyright (C) 2020  Klayton Killough
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published
 *  by the Free Software Foundation, version 3 of the License.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

package to.klay.wspro.core.game;

import com.google.common.eventbus.EventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.PaperCard;
import to.klay.wspro.core.api.game.GameStatus;
import to.klay.wspro.core.api.game.field.Zones;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.actions.PlayChoice;
import to.klay.wspro.core.game.actions.PlayChoiceAction;
import to.klay.wspro.core.game.actions.PlayChooser;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.commands.Commands;
import to.klay.wspro.core.game.formats.standard.phases.PhaseHandler;
import to.klay.wspro.core.game.formats.standard.phases.TurnPhase;
import to.klay.wspro.core.game.formats.standard.triggers.GameOverTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import to.klay.wspro.core.game.formats.standard.triggers.listeners.StandardWeissTriggerObservers;
import to.klay.wspro.core.game.formats.standard.zones.StandardWeissPlayArea;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import static to.klay.wspro.core.game.actions.PlayChooser.SelectionType.UP_TO;
import static to.klay.wspro.core.game.formats.standard.triggers.TriggerCause.GAME_ACTION;

public class Game {

    private static final Logger log = LogManager.getLogger();
    private static final int GUID_NAME_COMPLEXITY = 4;


    private final GamePlayer player1, player2;
    private final AbilityFinder abilityFinder;
    private EventBus TriggerManager;
    private PhaseHandler phaseHandler;
    private TimingManager timingManager;

    TurnPhase currentPhase;
    private GameStatus gameStatus;
    private Random random;
    private int fundamentalOrderCounter = 0;
    private String gameID;
    private final List<GamePlayer> losingPlayers = new ArrayList<>();
    private final List<GamePlayer> players;

    public Game(Player player1, Player player2, AbilityFinder abilityFinder){

        this.player1 = new GamePlayer(this, player1);
        this.player2 = new GamePlayer(this, player2);
        players = Arrays.asList(this.player1, this.player2);

        this.abilityFinder = abilityFinder;
        gameStatus = GameStatus.NOT_READY;
        setup();
    }


    /////////////game setup and running methods

    private void setup(){

        initializePlayingField();

        //todo obtain decks

        TriggerManager = new EventBus("Game Trigger Manager"){

        };
        phaseHandler = new PhaseHandler(this);
        timingManager = new TimingManager(this);
        new StandardWeissTriggerObservers(this);

        gameID = getRandomGuid();
        log.debug("Game ID created: " + gameID);

        gameStatus = GameStatus.READY;
    }


    /**
    *              -begin game setup interactions *(overridable method)<br>
        - throwEx decks, place on deck zone
        - randomly determine who goes first (*overridable method)
        - draw 5 and mill, redraw up to 5
     * @param currentTurnPlayer
     * @param opposingPlayer

     */
    private void pregame(GamePlayer currentTurnPlayer, GamePlayer opposingPlayer) {

        //place respective decks into respective deck zones
        List<GamePlayer> players = Arrays.asList(currentTurnPlayer, opposingPlayer);
        for (GamePlayer player : players) {
            for (PaperCard paperCard : player.getPaperDeck().getCards()) {
                player.getPlayArea().getPlayZone(Zones.ZONE_DECK).add(new PlayingCard(this, paperCard, player, player));
            }
        }

        //shuffle the decks and draw the starting hand
        for (GamePlayer player1 : players ) {
            Commands.shuffleZone(player1.getPlayArea().getPlayZone(Zones.ZONE_DECK), GAME_ACTION, player1);
            for (int i = 0; i < 5; i++) {
                Commands.drawCard(player1, GAME_ACTION, player1);
            }
        }

        //choose any amount of cards in hand to exchange
        for (GamePlayer player : players) {
            List<PlayChoice> choices = player.getPlayArea().getPlayZone(Zones.ZONE_HAND).getContents()
                    .stream().map(PlayChoice::makeCardChoice).collect(Collectors.toList());
            PlayChoice noMulliganChoice = PlayChoice.makeActionChoice(PlayChoiceAction.END_ACTION);
            choices.add(noMulliganChoice);
            PlayChooser chooser = new PlayChooser(choices, UP_TO, choices.size()-1);

            //ask player
            List<PlayChoice> decisions = Commands.makePlayChoice(player, chooser);

            if (!decisions.contains(noMulliganChoice)){
                // send cards to waiting room, draw an equal amount back
                for (PlayChoice decision : decisions) {
                    Commands.discardCard(player, decision.getCard(), GAME_ACTION, player);
                }

                for (PlayChoice decision : decisions) {
                    Commands.drawCard(player, GAME_ACTION, player);
                }
            }
        }
    }

    /**
     * Starts the duel, choosing a random starting player. Blocks until the game is finished.
     */
    public void startGame() {
        List<GamePlayer> player1s = new ArrayList<>(getPlayers());
        Collections.shuffle(player1s, getRandom());
        startGame(player1s.get(0));
    }

    /**
     * Starts the duel with a specified player. Blocks until the game is finished
     * @param startingPlayer player to take first turn, if null a random player is chosen
     */
    public void startGame(GamePlayer startingPlayer){

        if (isGameOver()) return;

        GamePlayer currentTurnPlayer = (player1 == startingPlayer) ? player1 : player2;
        GamePlayer opposingPlayer = (player1 == startingPlayer) ? player2 : player1;

        try {
            pregame(currentTurnPlayer, opposingPlayer);
            phaseHandler.startFirstTurn(currentTurnPlayer, opposingPlayer);
        } catch (RuntimeException exception) {
            //arguably, this should only catch GameRuntimeExceptions...
            unexpectedEndGame(exception);
        }
    }



    public void unexpectedEndGame(RuntimeException ex){
        log.error("Stopping Game " + this + " Due to exception: " + ex.getMessage(), ex);
        gameStatus = GameStatus.FINISHED_UNEXPECTEDLY;
        //announce game over to everyone regardless of their state
        GameOverTrigger trigger = new GameOverTrigger(Collections.emptyList(), TriggerCause.GAME_ACTION, getCurrentTurnPlayer());
        getTriggerManager().post(trigger);
        //todo code in unexpected endGame functionality, no winner, preform log dump & cleanup (if any)
    }

    public void endGame(){
        gameStatus = GameStatus.FINISHED_SUCCESSFULLY;
        //at this point LosingPlayers have been populated, game status is finished.
        //todo  preform log dump(?) & cleanup (if any)
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
        return phaseHandler.getCurrentTurnPlayer();
    }

    public GamePlayer getNonTurnPlayer() {
        return phaseHandler.getNonTurnPlayer();
    }

    //@Override

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public GameStatus getGameState() {
        return gameStatus;
    }

    public boolean isGameOver() {
        return gameStatus == GameStatus.FINISHED_SUCCESSFULLY || gameStatus == GameStatus.FINISHED_UNEXPECTEDLY;
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
     * stops the freeze on  Interrupt type rule actions
     */
    public void disableInterruptLock() {
        timingManager.disableInterruptLock();
    }

    public void enableSimultaneousLock() {
        timingManager.enableSimultaneousLock();
    }

    /**
     * stops the freeze on  Interrupt type rule actions and continious effects from updating.
     */
    public void disableSimultaneousLock() {
        timingManager.disableSimultaneousLock();
    }

    public void continuousTiming() {
        timingManager.doContinuousTiming();
    }

    public TimingManager getTimingManager() {
        return timingManager;
    }

    public String getRandomGuid(){
        byte[] byteHolder = new byte[GUID_NAME_COMPLEXITY];
        getRandom().nextBytes(byteHolder);
        return UUID.nameUUIDFromBytes(byteHolder).toString();
    }

    public List<GamePlayer> getLosingPlayers() {
        return losingPlayers;
    }

    public AbilityFinder getAbilityFinder() {
        return abilityFinder;
    }

    public List<GamePlayer> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public int getCurrentTurnNumber() {
        return phaseHandler.getTurnNumber();
    }

    public String getGameID() {
        return gameID;
    }
}

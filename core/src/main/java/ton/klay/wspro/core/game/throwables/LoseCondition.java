package ton.klay.wspro.core.game.throwables;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.GameRuntimeException;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.LoseConditions;
import ton.klay.wspro.core.game.Duel;

/**
 * Represets a Lose Condition  in the game which can be thrown to end the game immediately and proceed to endgame cleanup
 * @see Duel#endGame(LoseCondition)
 * @see LoseConditions
 */
public class LoseCondition extends GameRuntimeException {

    private static final Logger log = LogManager.getLogger();


    LoseConditions lostCondition;
    GamePlayer[] losingPlayers;

    public LoseCondition(LoseConditions losingReason, GamePlayer losingPlayer){
        this(losingReason, new GamePlayer[]{losingPlayer});
    }
    public LoseCondition(LoseConditions losingReason, GamePlayer[] losingPlayers){
        lostCondition = losingReason;
        this.losingPlayers = losingPlayers;
    }

    public LoseConditions getLostCondition() {
        return lostCondition;
    }


    //todo set a logger message and a custom exception message for the losing reason.
}

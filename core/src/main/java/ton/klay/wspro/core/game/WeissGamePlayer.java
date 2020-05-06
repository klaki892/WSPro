package ton.klay.wspro.core.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.IDeck;
import ton.klay.wspro.core.api.game.communication.Communicator;
import ton.klay.wspro.core.api.game.field.PlayArea;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.player.PlayerDeckPair;

public class WeissGamePlayer implements GamePlayer {

    private static final Logger log = LogManager.getLogger();
    private Communicator communicator;
    private PlayArea playArea;
    private IDeck deck;


    public WeissGamePlayer(PlayerDeckPair playerDeckPair) {

        this.communicator = playerDeckPair.getCommunicator();

        this.deck = playerDeckPair.getDeck();
        //todo special command to request deck list...probably in utilities

    }

    @Override
    public PlayArea getPlayArea() {
        return playArea;
    }

    @Override
    public Communicator getCommunicator() {
        return communicator;
    }

    @Override
    public IDeck getDeck() {
        return deck;
    }

    @Override
    public void setPlayArea(PlayArea playArea) {
        this.playArea = playArea;
    }

    @Override
    public void setCommunicator(Communicator communicator) {
        this.communicator = communicator;
    }
}

package ton.klay.wspro.core.game.formats.standard.phases;

import com.google.common.base.MoreObjects;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ton.klay.wspro.core.api.cards.MockCharacterPaperCard;
import ton.klay.wspro.core.api.game.GameStatus;
import ton.klay.wspro.core.api.game.IDeck;
import ton.klay.wspro.core.api.game.field.PlayArea;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.player.PlayerController;
import ton.klay.wspro.core.api.game.player.PlayerControllerTest;
import ton.klay.wspro.core.game.Game;
import ton.klay.wspro.core.game.actions.PlayChoice;
import ton.klay.wspro.core.game.actions.PlayChooser;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.PhaseStartedTrigger;
import ton.klay.wspro.core.game.formats.standard.zones.DeckZone;
import ton.klay.wspro.core.game.formats.standard.zones.StandardWeissPlayArea;

import java.util.List;

class PhaseHandlerTests  {

    GamePlayer player1;
    GamePlayer player2;
    Game game;

    public static void main(String[] args) {
        System.setProperty("org.apache.logging.log4j.simplelog.StatusLogger.level", "INFO");
        PhaseHandlerTests instance = new PhaseHandlerTests();
        instance.setUp();
        instance.blankGameCompletion();
    }

    @BeforeEach
    void setUp() {
        player1 = new TestGamePlayer();
        player2 = new TestGamePlayer();
        EventBus bus = new EventBus();

        game = new Game(player1, player2){

            @Override
            public EventBus getTriggerManager() {
                return bus;
            }

            @Override
            public void startGame(GamePlayer startingPlayer) {
                bus.register(new Object(){
                    @Subscribe
                    public void deadListener(BaseTrigger t){
                        if (t instanceof PhaseStartedTrigger){
                            PhaseStartedTrigger t1 = (PhaseStartedTrigger) t;
                            System.out.println(MoreObjects.toStringHelper(t1)
                                    .add("name", t.getTriggerName().name())
                                    .add("Cause", t.getCause())
                                    .add("caller", t.getCaller().getClass().getSimpleName())
                                    .add("turnPlayer", t1.getTurnPlayer())
                                    .toString());
                            return;

                        }
                        System.out.println(MoreObjects.toStringHelper(t)
                                .add("name", t.getTriggerName().name())
                                .add("Cause", t.getCause())
                                .add("caller", t.getCaller().getClass().getSimpleName())
                                .toString());
                    }

                    @Subscribe
                    public void forceEnd(PhaseStartedTrigger t){
//                        if (t.getPhase() == TurnPhase.END_PHASE)
//                            throw new GameRuntimeException("ending");
                    }
                });
                super.startGame(startingPlayer);
            }
        };

    };


    PhaseHandler phaseHandler = new PhaseHandler(game);

    @Test
    void blankGameCompletion() {
        game.startGame(player1);
        Assertions.assertSame(GameStatus.FINISHED_SUCCESSFULLY, game.getGameState() );
        System.out.print("Losing Players:");
        if (game.getLosingPlayers().contains(player1)) System.out.print("Player 1");
        if (game.getLosingPlayers().contains(player2)) System.out.print("Player 2");
        System.out.println();

    }

    private class TestGamePlayer implements GamePlayer {

        boolean init = false;

        DeckZone deckZone = new DeckZone(this);


        PlayArea area = new StandardWeissPlayArea(this){
            @Override
            public PlayZone getPlayZone(Zones zone) {
                if (zone == Zones.ZONE_DECK){
                    if (!init){
                        init = true;
                        for (int i = 0; i < 50; i++) {
                            deckZone.add(new PlayingCard(game,
                                    new MockCharacterPaperCard(), player1, player1));
                        }
                        return deckZone;
                    } else {
                        return deckZone;
                    }
                }
                return super.getPlayZone(zone);
            }
        };
        PlayerController controller = new PlayerController() {
            @Override
            public List<PlayChoice> makePlayChoice(PlayChooser chooser) {
                return PlayerControllerTest.commandLinePlayChoiceMaker(chooser);
            }
        };
        @Override
        public PlayArea getPlayArea() {
            return area;
        }

        @Override
        public PlayerController getController() {
            return controller;
        }

        @Override
        public IDeck getDeck() {
            return null;
        }

        @Override
        public void setPlayArea(PlayArea playArea) {

        }

        @Override
        public Game getGame() {
            return game;
        }

        @Override
        public GamePlayer getMaster() {
            return this;
        }
    }
}
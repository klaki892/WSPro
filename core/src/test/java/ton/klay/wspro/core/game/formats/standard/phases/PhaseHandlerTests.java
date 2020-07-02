package ton.klay.wspro.core.game.formats.standard.phases;

import com.google.common.base.MoreObjects;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ton.klay.wspro.core.api.cards.*;
import ton.klay.wspro.core.api.game.GameStatus;
import ton.klay.wspro.core.api.game.IDeck;
import ton.klay.wspro.core.api.game.communication.Communicator;
import ton.klay.wspro.core.api.game.field.PlayArea;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.player.PlayerController;
import ton.klay.wspro.core.api.scripting.cards.CardType;
import ton.klay.wspro.core.game.Game;
import ton.klay.wspro.core.game.cardLogic.ability.AutomaticAbility;
import ton.klay.wspro.core.game.events.InterruptRuleAction;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.PhaseStartedTrigger;
import ton.klay.wspro.core.game.formats.standard.zones.DeckZone;
import ton.klay.wspro.core.game.formats.standard.zones.StandardWeissPlayArea;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

class PhaseHandlerTests  {

    GamePlayer player1;
    GamePlayer player2;
    Game game;

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
                        for (int i = 0; i < 30; i++) {
                            deckZone.add(new PlayingCard(game,
                                    new PaperCard() {
                                @Override
                                public Collection<LocalizedString> getCardName() {
                                    return null;
                                }

                                @Override
                                public int getLevel() {
                                    return 0;
                                }

                                @Override
                                public Cost getCost() {
                                    return null;
                                }

                                @Override
                                public CardIcon getIcon() {
                                    return null;
                                }

                                @Override
                                public Collection<CardTrigger> getTriggerIcons() {
                                    return null;
                                }

                                @Override
                                public int getPower() {
                                    return 0;
                                }

                                @Override
                                public int getSoul() {
                                    return 0;
                                }

                                @Override
                                public Collection<LocalizedString> getTraits() {
                                    return null;
                                }

                                @Override
                                public CardColor getColor() {
                                    return null;
                                }

                                @Override
                                public CardType getCardType() {
                                    return null;
                                }

                                @Override
                                public Collection<LocalizedString> getTitleName() {
                                    return null;
                                }

                                @Override
                                public String getID() {
                                    return null;
                                }

                                @Override
                                public CardAffiliation getAffiliations() {
                                    return null;
                                }
                            }, player1, player1));
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
            public InterruptRuleAction chooseInterruptRuleAction(List<InterruptRuleAction> interruptRuleActions) {
                System.out.println(interruptRuleActions);
                return null;
            }

            @Override
            public AutomaticAbility chooseAutomaticAbilityToPerform(List<AutomaticAbility> interruptRuleActions) {
                System.out.println(interruptRuleActions);
                return null;
            }

            @Override
            public Optional<PlayingCard> chooseClockCard(List<PlayingCard> cards) {
//                return Optional.empty();
                return Optional.of(cards.get(0));
            }

            @Override
            public Optional<PlayingCard> chooseClimaxPhaseCard(List<PlayingCard> climaxCards) {
                return Optional.empty();
            }

            @Override
            public PlayingCard chooseLevelUpCard(List<PlayingCard> clockCards) {
                return clockCards.get(0);
            }
        };
        @Override
        public PlayArea getPlayArea() {
            return area;
        }

        @Override
        public Communicator getCommunicator() {
            return null;
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
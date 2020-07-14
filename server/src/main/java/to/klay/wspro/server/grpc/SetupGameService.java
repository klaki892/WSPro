package to.klay.wspro.server.grpc;

import com.google.rpc.DebugInfo;
import io.grpc.Metadata;
import io.grpc.protobuf.ProtoUtils;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.PaperCard;
import to.klay.wspro.server.ServerGameManager;
import to.klay.wspro.server.game.PaperDeckBuilder;
import to.klay.wspro.server.game.ServerGame;
import to.klay.wspro.server.game.ServerPlayer;
import to.klay.wspro.server.grpc.setupgame.CardIdList;
import to.klay.wspro.server.grpc.setupgame.GameSetupStatus;
import to.klay.wspro.server.grpc.setupgame.GrpcGameStartRequest;
import to.klay.wspro.server.grpc.setupgame.GrpcGameStartResponse;
import to.klay.wspro.server.grpc.setupgame.PlayerInfo;
import to.klay.wspro.server.grpc.setupgame.SetupGameGrpc;
import to.klay.wspro.server.grpc.setupgame.SetupGameRequest;
import to.klay.wspro.server.setup.finders.cards.CardFinderDeckResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SetupGameService extends SetupGameGrpc.SetupGameImplBase {

    private static final Metadata.Key<DebugInfo> DEBUG_INFO_TRAILER_KEY =
            ProtoUtils.keyForProto(DebugInfo.getDefaultInstance());

    private static final DebugInfo DEBUG_INFO =
            DebugInfo.newBuilder()
                    .addStackEntries("stack_entry_1")
                    .addStackEntries("stack_entry_2")
                    .addStackEntries("stack_entry_3")
                    .setDetail("detailed error info.").build();

    private static final String DEBUG_DESC = "detailed error description";

    private static final Logger log = LogManager.getLogger();
    private final ServerGameManager serverGameManager;

    public SetupGameService(ServerGameManager serverGameManager){

        this.serverGameManager = serverGameManager;
    }

    @Override
    public void requestGame(SetupGameRequest request, StreamObserver<GameSetupStatus> responseObserver) {

        responseObserver.onNext(validateGameRequest(request));
        responseObserver.onCompleted();

    }

    @Override
    public void startGame(GrpcGameStartRequest request, StreamObserver<GrpcGameStartResponse> responseObserver) {
        //if the game is pending, start it
        GrpcGameStartResponse response;
        String gameID = request.getGameIdentifier().trim();

        Optional<ServerGame> possiblePendingGame = serverGameManager.getPendingGame(gameID);
        if (possiblePendingGame.isPresent()){
            ServerGame pendingGame = possiblePendingGame.get();
            if (pendingGame.isStartable()){
                pendingGame.forceStartGame();
                if (serverGameManager.setGameActive(gameID)) {
                    response = GrpcGameStartResponse.newBuilder().setDidGameStart(true).build();
                } else {
                    response = GrpcGameStartResponse.newBuilder().setDidGameStart(false).build();
                }
            } else{
                response = GrpcGameStartResponse.newBuilder().setDidGameStart(false).build();
            }
        } else {
            log.error("asked to start game " + gameID + " But game isnt pending");
            response = GrpcGameStartResponse.newBuilder().setDidGameStart(false).build();
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private GameSetupStatus validateGameRequest(SetupGameRequest request) {
        PlayerInfo requestPlayer1 = request.getPlayer1();
        PlayerInfo requestPlayer2 = request.getPlayer2();

        //check that we are accepting new games
        if (!serverGameManager.canMakeGame()) {
            return invalidRequest("Server is not accepting any new game requests");
        } else {
            return makeGame(requestPlayer1, requestPlayer2);
        }
    }

    private GameSetupStatus makeGame(PlayerInfo requestPlayer1, PlayerInfo requestPlayer2) {
        //get the players in game-ready state
        Set<String> invalidCardIdList = new HashSet<>();
        Set<String> nonAbilityCards = new HashSet<>();

        List<PlayerInfo> requestPlayers = Arrays.asList(requestPlayer1, requestPlayer2);
        List<List<PaperCard>> decks = new ArrayList<>(2);

        for (PlayerInfo requestPlayer : requestPlayers) {
            //check all fields are there
            if (!requestPlayer.hasDeck() || requestPlayer.getPlayerName().isEmpty()) {
                return invalidRequest("A Player in a request is missing required information");
            }

            //source card information
            List<String> cardIdList = requestPlayer.getDeck().getCardList().getCardIDList();
            CardFinderDeckResult deckFindResult = serverGameManager.getCardFinder().sourceDeck(cardIdList);

            //grab the cards and report the ID's of cards that couldnt be sourced.
            List<PaperCard> cardList = deckFindResult.getFoundCards();
            invalidCardIdList.addAll(deckFindResult.getNotFoundIds());

            //all cards have been found, check for ability definitions
            for (PaperCard card : cardList) {
                if (!serverGameManager.getAbilityFinder().doesScriptExist(card)) {
                    nonAbilityCards.add(card.getID());
                }
            }

            decks.add(cardList);

        }

        if (invalidCardIdList.size() > 0){
            return unsourcedCardsResponse(invalidCardIdList);
        }

        //create the Players and the game
        ServerPlayer player1 = new ServerPlayer(requestPlayer1.getPlayerName());
        player1.setDeck(PaperDeckBuilder.createPaperDeck(requestPlayer1.getDeck().getDeckName(), decks.get(0)));

        ServerPlayer player2 = new ServerPlayer(requestPlayer2.getPlayerName());
        player2.setDeck(PaperDeckBuilder.createPaperDeck(requestPlayer2.getDeck().getDeckName(), decks.get(1)));

        ServerGame game = new ServerGame(serverGameManager, player1, player2);

        GameSetupStatus.Builder statusBuilder = GameSetupStatus.newBuilder();

        //game is now ready- if the server accepts it
        if (serverGameManager.addReadyGame(game)){
            statusBuilder.setGameReady(true);
            statusBuilder.setGameIdentifier(game.getGameID());
        } else {
            return invalidRequest("Server didnt accept game readyable game request.");
        }

        if (nonAbilityCards.size() != 0) {
            statusBuilder.setNonScriptedCards(
                    CardIdList.newBuilder().addAllCardID(nonAbilityCards).build());
        }

        return statusBuilder.build();
    }

    private static GameSetupStatus unsourcedCardsResponse(Set<String> invalidCardIdList) {
        CardIdList cardList = CardIdList.newBuilder()
                .addAllCardID(invalidCardIdList)
                .build();

        GameSetupStatus setupStatus = GameSetupStatus.newBuilder()
                .setUnknownCards(cardList)
                .setErrorMessage("Server could not find definitions for some cards")
                .setGameReady(false)
                .build();
        log.debug(setupStatus);
        return setupStatus;
    }

    private static GameSetupStatus invalidRequest(String errorMessage) {
        GameSetupStatus setupStatus = GameSetupStatus.newBuilder()
                .setGameReady(false)
                .setErrorMessage(errorMessage)
                .build();
        log.info(setupStatus);
        return setupStatus;
    }
}

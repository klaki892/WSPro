package to.klay.wspro.server.grpc;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import com.google.protobuf.Empty;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.server.ServerGameManager;
import to.klay.wspro.server.game.GrpcPlayerController;
import to.klay.wspro.server.game.ServerGame;
import to.klay.wspro.server.game.ServerPlayer;
import to.klay.wspro.server.grpc.gameplay.GrpcGameConnectRequest;
import to.klay.wspro.server.grpc.gameplay.GrpcGameConnectResponse;
import to.klay.wspro.server.grpc.gameplay.GrpcGameMessage;
import to.klay.wspro.server.grpc.gameplay.GrpcPlayResponse;
import to.klay.wspro.server.grpc.gameplay.GrpcPlayerToken;
import to.klay.wspro.server.grpc.gameplay.GrpcSuccessResponse;
import to.klay.wspro.server.grpc.gameplay.PlayWeissServiceGrpc;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PlayWeissService extends PlayWeissServiceGrpc.PlayWeissServiceImplBase {

    private static final Logger log = LogManager.getLogger();
    private final ServerGameManager serverGameManager;
    private final BiMap<GrpcPlayerToken, GrpcPlayerController> tokenControllerMap;

    public PlayWeissService(ServerGameManager serverGameManager){
        this.serverGameManager = serverGameManager;
        tokenControllerMap = Maps.synchronizedBiMap(HashBiMap.create());
    }


    @Override
    public void connectToGame(GrpcGameConnectRequest request, StreamObserver<GrpcGameConnectResponse> responseObserver) {
        responseObserver.onNext(establishGameConnection(request));
        responseObserver.onCompleted();
    }

    @Override
    public void readyUp(GrpcPlayerToken request, StreamObserver<GrpcSuccessResponse> responseObserver) {
        GrpcPlayerController controller = tokenControllerMap.get(request);
        GrpcSuccessResponse.Builder responseBuilder = GrpcSuccessResponse.newBuilder();
        if (controller != null){
            controller.setPlayerReadied(true);
            responseBuilder.setWasSuccessful(true);
            log.info(String.format("Player (%s) Readied up for Game %s", request.getPlayerName() ));
        } else {
            responseBuilder.setWasSuccessful(false);
            log.error(String.format("Player (%s) attempted to ready up but no controller assoicated with token (%s)",
                    request.getPlayerName(), request.getToken()));
        }
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void listenToGameEvents(GrpcPlayerToken request, StreamObserver<GrpcGameMessage> responseObserver) {
        GrpcPlayerController controller = tokenControllerMap.get(request);

        if (controller != null) {
            boolean gameEnded = false;
            do {
                //look for messages from the game and forward them
                try {
                    GrpcGameMessage gameMessage = controller.getGameEventQueue().poll(30, TimeUnit.SECONDS);

                    if (gameMessage != null) {
                        //todo we need a better check
                        if (isGameOverMessage(gameMessage)) {
                            gameEnded = true;
                        }
                        responseObserver.onNext(gameMessage);
                    } else {
                        //check to make sure the game is alive or pending
                        Optional<ServerGame> activeGame = serverGameManager.getActiveGame(controller.getGame().getGameID());
                        Optional<ServerGame> pendingGame = serverGameManager.getPendingGame(controller.getGame().getGameID());
                        if (!activeGame.isPresent() && !pendingGame.isPresent()){
                            log.warn(String.format(
                                    "Listening game stream for player %s" +
                                    " noticed game %s is not active, but wasnt told game is no longer active.",
                                    request.getPlayerName(), controller.getGame().getGameID()
                            ));
                            gameEnded = true;
                        }
                    }
                } catch (InterruptedException e) {
                    log.error("error while listening to game stream for game" + controller.getGame().getGameID(), e);
                    responseObserver.onError(new StatusRuntimeException(Status.INTERNAL));
                    return;
                }

            } while (!gameEnded);
        } else {
            log.error("No/invalid token was mapped to this request.");
            responseObserver.onError(new StatusRuntimeException(Status.NOT_FOUND));
        }
        responseObserver.onCompleted();


    }

    private boolean isGameOverMessage(GrpcGameMessage gameMessage) {
        if (gameMessage.hasTrigger()){
            return gameMessage.getTrigger().getTriggerText().contains("GAME_OVER");
        }
        return false;
    }

    @Override
    public void answerPlayRequest(GrpcPlayResponse request, StreamObserver<Empty> responseObserver) {
        //lookup controller and send answer
        GrpcPlayerController controller = tokenControllerMap.get(request.getToken());
        if (controller != null) {
            //forward answer
            //FIXME this is dangerous, play responses should have unique ID's else repeat answers are possible
            controller.answerPlayRequest(request);
        } else {
            log.error("Received invalid play response. No controller was mapped to this request:\n" + request);
        }

        responseObserver.onNext(Empty.newBuilder().getDefaultInstanceForType());
        responseObserver.onCompleted();
    }

    private void handlePlayerResponse(GrpcPlayResponse request) {
    }

    private GrpcGameConnectResponse establishGameConnection(GrpcGameConnectRequest request) {
        String gameIdentifier = request.getGameIdentifier().trim();
        String playerName = request.getPlayerName().trim();
        Optional<ServerGame> pendingGame = serverGameManager.getPendingGame(gameIdentifier);

        if (!pendingGame.isPresent()){
            log.info("Player (" + playerName + ") tried to connect to a non-existing game (" + gameIdentifier +")");
            return GrpcGameConnectResponse.newBuilder().setConnectionAccepted(false).build();
        }

        String newTokenString = UUID.randomUUID().toString();

        GrpcPlayerToken token = GrpcPlayerToken.newBuilder()
                .setPlayerName(playerName)
                .setToken(newTokenString).build();

        //game is present, let them in
        Optional<ServerPlayer> gamePlayer = pendingGame.get().getPlayer(playerName);
        if (gamePlayer.isPresent()){

            //register communicator if not already there
            if (gamePlayer.get().getController() == null){
                GrpcPlayerController controller = new GrpcPlayerController(pendingGame.get().getWeissGame());
                        gamePlayer.get().setController(controller);
                tokenControllerMap.put(token, controller);
            } else {
                log.warn(String.format("Player %s already had a controller when player requeusted a game connection, issuing out same token.", playerName));
                //noinspection SuspiciousMethodCalls
                token = tokenControllerMap.inverse().get(gamePlayer.get().getController());
            }
        } else{
            log.error(String.format("Player %s tried connecting to a game(%s), but they weren't a player. (spectator?)",
                    playerName, gameIdentifier));
            return GrpcGameConnectResponse.newBuilder().setConnectionAccepted(false).build();
        }

        log.info(String.format("Player %s connected to %s", playerName, gameIdentifier));
        return GrpcGameConnectResponse.newBuilder()
                .setConnectionAccepted(true)
                .setToken(token)
                .build();
    }
}

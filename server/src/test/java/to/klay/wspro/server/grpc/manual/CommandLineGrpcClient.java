package to.klay.wspro.server.grpc.manual;

import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.server.grpc.gameplay.GrpcGameMessage;
import to.klay.wspro.server.grpc.gameplay.GrpcPlayerToken;
import to.klay.wspro.server.grpc.gameplay.GrpcSuccessResponse;

public class CommandLineGrpcClient {

    private static final Logger log = LogManager.getLogger();

    SetupGameTestClient setupGameTestClient;
    PlayWeissTestClient playWeissTestClient;

    public CommandLineGrpcClient(String localhost, int port){
        setupGameTestClient = new SetupGameTestClient(localhost, port);
        playWeissTestClient = new PlayWeissTestClient(localhost, port);
    }

    public GrpcPlayerToken GetPlayerToken(String playerName, String id) {
        return playWeissTestClient.connectToGame(playerName, id).getToken();
    }

    public GrpcSuccessResponse readyUp(GrpcPlayerToken token) {
        return playWeissTestClient.readyUp(token);

    }


    public void getGameEvents(String player, String ID){
        getGameEvents(GrpcPlayerToken.newBuilder().setPlayerName(player).setToken(ID).build());
    }
    public void getGameEvents(GrpcPlayerToken token){
        playWeissTestClient.getEventListener(token.getToken(), token.getPlayerName(), new StreamObserver<GrpcGameMessage>() {
            @Override
            public void onNext(GrpcGameMessage grpcGameMessage) {
                System.out.println(grpcGameMessage);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
                System.exit(0);
            }

            @Override
            public void onCompleted() {
                System.out.println("Conncetion Closed");
                System.exit(0);
            }
        });
    }

    public void sendResponse(GrpcPlayerToken token, int nextInt) {
        playWeissTestClient.sendAnswer(token, nextInt);
    }

    public void startGame(String gameToken) {
        setupGameTestClient.startGame(gameToken);
    }
}

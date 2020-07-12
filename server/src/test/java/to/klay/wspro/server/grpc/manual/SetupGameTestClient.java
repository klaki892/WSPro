package to.klay.wspro.server.grpc.manual;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.server.grpc.setupgame.CardIdList;
import to.klay.wspro.server.grpc.setupgame.Deck;
import to.klay.wspro.server.grpc.setupgame.GameSetupStatus;
import to.klay.wspro.server.grpc.setupgame.GrpcGameStartRequest;
import to.klay.wspro.server.grpc.setupgame.PlayerInfo;
import to.klay.wspro.server.grpc.setupgame.SetupGameGrpc;
import to.klay.wspro.server.grpc.setupgame.SetupGameRequest;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

public class SetupGameTestClient implements Closeable {

    private static final Logger log = LogManager.getLogger();
    private final ManagedChannel channel;
    private final SetupGameGrpc.SetupGameBlockingStub blockingStub;
    private final SetupGameGrpc.SetupGameStub asyncStub;

    public SetupGameTestClient(String host, int port){
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext());
    }

    public SetupGameTestClient(ManagedChannelBuilder<?> channelBuilder){
        channel = channelBuilder.build();
        blockingStub = SetupGameGrpc.newBlockingStub(channel);
        asyncStub = SetupGameGrpc.newStub(channel);

    }

    public GameSetupStatus setupGame(String p1, String p2, List<String> deck){
        Deck.Builder deckBuilder = Deck.newBuilder().setCardList(CardIdList.newBuilder().addAllCardID(deck).build());
        PlayerInfo player1 = PlayerInfo.newBuilder().setDeck(deckBuilder).setPlayerName(p1).build();
        PlayerInfo player2 = PlayerInfo.newBuilder().setDeck(deckBuilder).setPlayerName(p2).build();

        return blockingStub.requestGame(SetupGameRequest.newBuilder()
                .setPlayer1(player1)
                .setPlayer2(player2)
                .build());

    }

    @Override
    public void close() throws IOException {
        channel.shutdown();
    }

    public void startGame(String gameToken) {
        System.out.println(blockingStub.startGame(
                GrpcGameStartRequest.newBuilder().setGameIdentifier(gameToken).build()).toString());
    }
}

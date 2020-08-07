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

package to.klay.wspro.server.grpc.manual;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.proto.GameMessageProto;
import to.klay.wspro.server.grpc.gameplay.GrpcGameConnectRequest;
import to.klay.wspro.server.grpc.gameplay.GrpcGameConnectResponse;
import to.klay.wspro.server.grpc.gameplay.GrpcPlayResponse;
import to.klay.wspro.server.grpc.gameplay.GrpcPlayerToken;
import to.klay.wspro.server.grpc.gameplay.GrpcSuccessResponse;
import to.klay.wspro.server.grpc.gameplay.PlayWeissServiceGrpc;

import java.io.Closeable;
import java.io.IOException;

public class PlayWeissTestClient implements Closeable {

    private static final Logger log = LogManager.getLogger();
    private final ManagedChannel channel;
    private PlayWeissServiceGrpc.PlayWeissServiceBlockingStub blockingStub;
    private PlayWeissServiceGrpc.PlayWeissServiceStub asyncStub;

    public PlayWeissTestClient(String host, int port){
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext());
    }

    public PlayWeissTestClient(ManagedChannelBuilder<?> channelBuilder){
        channel = channelBuilder.build();
        blockingStub = PlayWeissServiceGrpc.newBlockingStub(channel);
        asyncStub = PlayWeissServiceGrpc.newStub(channel);

    }
    public GrpcGameConnectResponse connectToGame(String playerName, String id){
        return blockingStub.connectToGame(GrpcGameConnectRequest.newBuilder()
                .setGameIdentifier(id)
                .setPlayerName(playerName)
                .build());
    }

    public void getEventListener(String token, String playerName, StreamObserver<GameMessageProto> observer) {
        GrpcPlayerToken playerToken = GrpcPlayerToken.newBuilder().setToken(token).setPlayerName(playerName).build();
        asyncStub.listenToGameEvents(playerToken, observer);
    }

    @Override
    public void close() throws IOException {
        channel.shutdown();
    }

    public GrpcSuccessResponse readyUp(GrpcPlayerToken token) {
        return blockingStub.readyUp(token);
    }

    public void sendAnswer(GrpcPlayerToken token, int nextInt) {
        asyncStub.answerPlayRequest(GrpcPlayResponse.newBuilder()
                .setToken(token)
                .addChoiceNumber(nextInt).build(), new StreamObserver<GrpcSuccessResponse>() {
            @Override
            public void onNext(GrpcSuccessResponse grpcSuccessResponse) {
            }

            @Override
            public void onError(Throwable throwable) {
                throw new RuntimeException(throwable);
            }

            @Override
            public void onCompleted() {
                System.out.println("Response Acknowledged");
            }
        });
    }
}

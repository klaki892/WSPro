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

package to.klay.wspro.server.grpc.automated;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import io.grpc.testing.GrpcCleanupRule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import to.klay.wspro.server.ServerGameManager;
import to.klay.wspro.server.grpc.SetupGameService;
import to.klay.wspro.server.grpc.setupgame.CardIdList;
import to.klay.wspro.server.grpc.setupgame.Deck;
import to.klay.wspro.server.grpc.setupgame.GameSetupStatus;
import to.klay.wspro.server.grpc.setupgame.PlayerInfo;
import to.klay.wspro.server.grpc.setupgame.SetupGameGrpc;
import to.klay.wspro.server.grpc.setupgame.SetupGameRequest;

import java.util.concurrent.TimeUnit;

public class SetupGameTests {

    private static final Logger log = LogManager.getLogger();

    @Rule
    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    private Server server;
    private ManagedChannel inProcessChannel;


    @Before
    public void setUp() throws Exception {
        String serverName = InProcessServerBuilder.generateName();
        Injector injector = Guice.createInjector(
                new AutomatedTestingModule()
        );


        ServerGameManager serverGameManager = injector.getInstance(ServerGameManager.class);
        server = InProcessServerBuilder.
                forName(serverName)
                .addService(new SetupGameService(serverGameManager)).build();

        server.start();
        inProcessChannel = grpcCleanup.register(
                InProcessChannelBuilder.forName(serverName).build());

    }

    @After
    public void tearDown() throws Exception {
        server.shutdown().awaitTermination(10, TimeUnit.SECONDS);
    }

    @Test
    public void requestGameTest(){

        Deck.Builder deck = Deck.newBuilder().setCardList(CardIdList.newBuilder().addCardID("TEST/TEST").build());
        PlayerInfo player1 = PlayerInfo.newBuilder().setDeck(deck).setPlayerName("TestPlayer1").build();
        PlayerInfo player2 = PlayerInfo.newBuilder().setDeck(deck).setPlayerName("TestPlayer2").build();


        SetupGameRequest setupGameRequest = SetupGameRequest.newBuilder()
                .setPlayer1(player1)
                .setPlayer2(player2)
                .build();

        SetupGameGrpc.SetupGameBlockingStub stub = SetupGameGrpc.newBlockingStub(inProcessChannel);

        GameSetupStatus request1 = stub.requestGame(setupGameRequest);

        //first game is made successfully
        Assert.assertNotNull(request1.getGameIdentifier());
        Assert.assertTrue(request1.getGameReady());

        //second game is not made
        GameSetupStatus request2 = stub.requestGame(setupGameRequest);
        Assert.assertFalse(request2.getGameReady());

    }
}

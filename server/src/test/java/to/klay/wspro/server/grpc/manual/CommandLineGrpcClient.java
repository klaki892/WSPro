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

import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.proto.GameMessageProto;
import to.klay.wspro.core.game.proto.ProtoPlayChoice;
import to.klay.wspro.server.grpc.gameplay.GrpcPlayerToken;
import to.klay.wspro.server.grpc.gameplay.GrpcSuccessResponse;

import java.util.List;

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
        playWeissTestClient.getEventListener(token.getToken(), token.getPlayerName(), new StreamObserver<GameMessageProto>() {
            @Override
            public void onNext(GameMessageProto gameMessageProto) {
                if (gameMessageProto.hasTrigger()) {
                    System.out.println(gameMessageProto.getTrigger());
                } else {
                    List<ProtoPlayChoice> playChoicesList = gameMessageProto.getRequest().getPlayChoicesList();
                    for (int i = 0; i < playChoicesList.size(); i++) {
                        System.out.println("#" + i + ": " + playChoicesList.get(i).toString().replaceAll("\\n",""));
                    }
                }
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

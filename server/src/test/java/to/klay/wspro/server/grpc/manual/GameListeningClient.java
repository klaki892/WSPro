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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.server.grpc.gameplay.GrpcPlayerToken;
import to.klay.wspro.server.grpc.gameplay.GrpcSuccessResponse;

import java.util.Scanner;

public class GameListeningClient {

    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        System.out.println("Local server port: ");
        int port = in.nextInt();
        in.nextLine();

        //connect to server, get player token, ready up, and start listening.
        CommandLineGrpcClient client = new CommandLineGrpcClient("localhost", port);

        GrpcSuccessResponse response = null;

            System.out.println("Input Player Name: " );
            String playerName = in.nextLine();
            System.out.println("Enter Game ID: ");
        String gameToken = in.nextLine();
        GrpcPlayerToken token =  client.GetPlayerToken(playerName, gameToken);

            response = client.readyUp(token);

        if (response.getWasSuccessful()) {

            //initate listening client
            new Thread() {
                @Override
                public void run() {
                    client.getGameEvents(token);
                }
            }.start();

            client.startGame(gameToken);

            //send numbers back in the corect format
            while (true) {
                System.out.println("Next Resposne: ");
                int nextInt = in.nextInt();
                in.nextLine();

                new CommandLineGrpcClient("localhost", port).sendResponse(token, nextInt);
            }
        } else {
            System.out.println(response);
            System.exit(-1);
        }
    }
}

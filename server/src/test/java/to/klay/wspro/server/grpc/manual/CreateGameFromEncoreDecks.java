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

import java.util.Scanner;

public class CreateGameFromEncoreDecks {

    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args) {
        //get Deck ID
        Scanner in = new Scanner(System.in);

        System.out.println("EncoreDeck API Url: ");
        String url = in.nextLine();

        System.out.println("Local server port: ");
        int port = in.nextInt();
        in.nextLine();

        String gameID = ManualTestAutomatedCommands.createGame(url, port);
        System.out.println(gameID);

    }
}

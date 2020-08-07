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

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.server.grpc.setupgame.GameSetupStatus;
import to.klay.wspro.server.input.encoredecks.EncoreCard;
import to.klay.wspro.server.input.encoredecks.EncoreDeck;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class ManualTestAutomatedCommands {

    private static final Logger log = LogManager.getLogger();

    /**
     * Will automatically initalize a game and will print out tokens and names for connecting.
     * @param deckUrl
     */
    public static String createGame(String deckUrl, int serverPort){

        List<String> idList = parseEncoreDeckForIds(deckUrl);

       try( SetupGameTestClient setupGameClient = new SetupGameTestClient("localhost", serverPort)) {

           GameSetupStatus gameSetupStatus = setupGameClient.setupGame("p1", "p2", idList);
           if (!gameSetupStatus.getGameReady()) {
               throw new IllegalStateException(gameSetupStatus.getErrorMessage());
           } else {
               String sb = "Connection Info" +
                       "Player 1 Name: " + "p1" +
                       "Player 2 Name: " + "p2" +
                       "Game ID: " + gameSetupStatus.getGameIdentifier();

               log.info(sb);
               System.out.println(sb);
               return gameSetupStatus.getGameIdentifier();
           }
       } catch (IOException e) {
           e.printStackTrace();
           throw new RuntimeException(e);
       }
    }

    public static List<String> parseEncoreDeckForIds(String deckUrl){
        EncoreDeck encoreDeck;
        //first ask for the deck
        try (InputStream encore = new URL(deckUrl).openStream()){
            encoreDeck = new Gson().fromJson(new InputStreamReader(encore), EncoreDeck.class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return encoreDeck.getCards().stream().map(EncoreCard::getAdjustedID).collect(Collectors.toList());
    }
}

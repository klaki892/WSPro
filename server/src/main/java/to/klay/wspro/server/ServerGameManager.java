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

package to.klay.wspro.server;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.server.game.ServerGame;
import to.klay.wspro.server.setup.finders.abilities.QueryableAbilityFinder;
import to.klay.wspro.server.setup.finders.cards.CardFinder;
import to.klay.wspro.server.setup.modules.ServerOptions;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ServerGameManager {
    private final int concurrentGameLimit;
    private final Map<String, ServerGame> activeGames;
    private final Map<String, ServerGame> pendingGames;
    private final QueryableAbilityFinder abilityFinder;
    private final CardFinder cardFinder;

    private static final Logger log = LogManager.getLogger();

    @Inject
    public ServerGameManager(@Named(ServerOptions.GAME_LIMIT_KEY) int concurrentGameLimit,
                             QueryableAbilityFinder abilityFinder,
                             CardFinder cardFinder){
        this.concurrentGameLimit = concurrentGameLimit;
        activeGames = new ConcurrentHashMap<>();
        pendingGames = new ConcurrentHashMap<>();
        this.abilityFinder = abilityFinder;
        this.cardFinder = cardFinder;
    }


    public synchronized Collection<ServerGame> getGameList() {
        return activeGames.values();
    }

    public synchronized int getConcurrentGameLimit() {
        return concurrentGameLimit;
    }

    public QueryableAbilityFinder getAbilityFinder() {
        return abilityFinder;
    }

    public CardFinder getCardFinder() {
        return cardFinder;
    }

    public synchronized boolean addReadyGame(ServerGame game) {

        if (canMakeGame()){
            pendingGames.put(game.getGameID(), game);
            return true;
        } else {
            return false;
        }
    }

    public synchronized boolean canMakeGame() {
        return activeGames.size() + pendingGames.size() < getConcurrentGameLimit();
    }

    public Optional<ServerGame> getActiveGame(String gameIdentifier) {
        return Optional.ofNullable(activeGames.get(gameIdentifier));
    }
    public Optional<ServerGame> getPendingGame(String gameIdentifier) {
        return Optional.ofNullable(pendingGames.get(gameIdentifier));
    }

    public synchronized void removeFinishedGame(String gameID) {
        log.info("Game " + gameID + " Reported finished state, removing from active game list.");
        ServerGame removedGame = activeGames.remove(gameID);

        if (removedGame == null){
            log.warn("We were told game " + gameID + " finished, but the game was not in the active game list.");
        }

        //todo if a single game server (e.g. kubernetes) begin shutdown sequence.
    }

    public boolean setGameActive(String gameID) {
        ServerGame serverGame = pendingGames.get(gameID);

        if (serverGame != null){
            pendingGames.remove(gameID);
            activeGames.put(gameID, serverGame);
        } else {
            log.error("Was told to start game " + gameID + " but it was not pending.");
            return false;
        }
        return true;
    }
}

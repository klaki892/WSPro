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

package to.klay.wspro.core.test.game.formats;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.game.phase.GamePhase;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.api.game.setup.GameFormat;
import to.klay.wspro.core.api.game.setup.GameFormats;
import to.klay.wspro.core.api.game.setup.GameLocale;

import java.util.Collection;

public class TestGameFormat implements GameFormat {

    private static final Logger log = LogManager.getLogger();

    @Override
    public GameFormats getName() {
        return GameFormats.STANDARD;
    }

    @Override
    public void setGameLocale(GameLocale gameLocale) {

    }

    @Override
    public GameLocale getGameLocale() {
        return null;
    }

    @Override
    public boolean requirementsMet(Collection<GamePlayer> players) {
        return false;
    }

    @Override
    public Collection<String> getErrors() {
        return null;
    }

    @Override
    public GamePhase getPhase(String phaseName) {
        return null;
    }

    @Override
    public String getFormatInfo() {
        return null;
    }
}

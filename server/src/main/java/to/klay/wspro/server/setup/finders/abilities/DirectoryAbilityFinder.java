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

package to.klay.wspro.server.setup.finders.abilities;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.PaperCard;
import to.klay.wspro.core.game.Game;
import to.klay.wspro.core.game.cardLogic.ability.TypedAbilityList;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.server.setup.modules.ServerOptions;

import javax.inject.Named;
import java.nio.file.Path;
import java.util.Collections;

public class DirectoryAbilityFinder implements QueryableAbilityFinder {

    private static final Logger log = LogManager.getLogger();
    private final Path directory;

    @Inject
    public DirectoryAbilityFinder(@Named(ServerOptions.ABILITY_SOURCE_DIRECTORY_PATH_KEY) Path path){

        directory = path;
    }

    @Override
    public boolean doesScriptExist(PaperCard card) {
        return false;
    }

    @Override
    public TypedAbilityList getAbilitiesForCard(Game game, PlayingCard card) {
        return new TypedAbilityList(Collections.emptyList());
    }
}

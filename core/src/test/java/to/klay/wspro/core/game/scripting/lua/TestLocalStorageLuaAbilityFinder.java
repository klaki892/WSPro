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

package to.klay.wspro.core.game.scripting.lua;

import org.junit.jupiter.api.Assertions;
import to.klay.wspro.core.api.cards.PaperCard;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

public class TestLocalStorageLuaAbilityFinder extends LuaAbilityFinder {

    private final File[] luaScripts;

    public TestLocalStorageLuaAbilityFinder(){
        File resourcesDirectory = new File("src/test/resources/lua");

        //only pull files, not directories
        luaScripts = resourcesDirectory.listFiles(file -> {
            return file.isFile() && file.getName().endsWith(".lua");
        });
    }


    @Override
    public Optional<String> getLuaScript(PaperCard card) {
        String luaScript = null;
        if (luaScripts == null) return Optional.empty();
        for (File scriptFile : luaScripts) {
            if (scriptFile.getName().split("\\.")[0].equalsIgnoreCase(card.getID())){
                try {
                    luaScript = new String(Files.readAllBytes(scriptFile.toPath()));
                    break;
                } catch (IOException e) {
                    Assertions.fail(e);
                }
            }
        }
        return Optional.ofNullable(luaScript);
    }

}
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
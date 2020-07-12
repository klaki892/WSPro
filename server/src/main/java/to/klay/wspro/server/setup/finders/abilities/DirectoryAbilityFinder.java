package to.klay.wspro.server.setup.finders.abilities;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.server.setup.modules.ServerOptions;
import ton.klay.wspro.core.api.cards.PaperCard;
import ton.klay.wspro.core.game.Game;
import ton.klay.wspro.core.game.cardLogic.ability.TypedAbilityList;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

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

package ton.klay.wspro.core.test.game.formats;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.phase.GamePhase;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.api.game.setup.GameFormat;
import ton.klay.wspro.core.api.game.setup.GameFormats;
import ton.klay.wspro.core.api.game.setup.GameLocale;

import java.util.Collection;
import java.util.List;

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
    public GamePhase getStartPhase() {
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

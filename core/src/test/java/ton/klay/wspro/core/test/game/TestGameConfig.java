package ton.klay.wspro.core.test.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.setup.GameConfig;

import java.util.Map;

public class TestGameConfig implements GameConfig {

    private static final Logger log = LogManager.getLogger();

    @Override
    public Map<String, String> getAllConfigurations() {
        return null;
    }
}

package ton.klay.wspro.core.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.commands.CommandSender;
import ton.klay.wspro.core.api.game.commands.CommandSenderType;

public class GameCommandSender implements CommandSender {

    private static final Logger log = LogManager.getLogger();

    @Override
    public String getName() {
        return "Game/Meta";
    }

    @Override
    public CommandSenderType getSenderType() {
        return CommandSenderType.GAME;
    }
}

package ton.klay.wspro.core.test.game.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.commands.CommandSender;
import ton.klay.wspro.core.api.game.commands.CommandSenderType;

public class testCommandSender implements CommandSender {

    private static final Logger log = LogManager.getLogger();

    @Override
    public String getName() {
        return "test command sender";
    }

    @Override
    public CommandSenderType getSenderType() {
        return CommandSenderType.PLAYER;
    }
}

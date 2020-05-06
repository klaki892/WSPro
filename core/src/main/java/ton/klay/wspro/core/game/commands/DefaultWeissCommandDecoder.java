package ton.klay.wspro.core.game.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.commands.CommandDecoder;
import ton.klay.wspro.core.api.game.commands.CommandExecution;
import ton.klay.wspro.core.api.game.commands.CommandSender;
import ton.klay.wspro.core.api.game.commands.Command;
import ton.klay.wspro.core.api.game.communication.Communicable;
import ton.klay.wspro.core.api.scripting.ScriptEngine;

import java.util.Map;
import java.util.Scanner;

public class DefaultWeissCommandDecoder implements CommandDecoder {

    private static final Logger log = LogManager.getLogger();
    private final Map<String, Class<? extends CommandExecution>> commandMap;
    private final ScriptEngine scriptEngine;


    public DefaultWeissCommandDecoder(ScriptEngine scriptEngine){
        commandMap = DefaultWeissCommandMap.obtain();
        this.scriptEngine = scriptEngine;
    }
//    @Override
    public Command decode(Communicable message, CommandSender sender) {
        //FIXME dont use a scanner, way too much overhead.
        Scanner scanner = new Scanner(message.toCommunicableString());

        String keyword = scanner.next();
        String[] args = scanner.hasNextLine() ? scanner.nextLine().trim().split(" ") : new String[0];

        boolean createdFromScript = false;

        Class<? extends CommandExecution> cex;
        if ((cex = commandMap.get(keyword)) == null){
            if ((cex = scriptEngine.getScriptedCommand(keyword)) != null)
                createdFromScript = true;
        }

        if (cex == null) {
            return null;
        } else {
            if (createdFromScript) {
                return DefaultCommandFactory.createFromScript(cex, keyword, args, sender, scriptEngine);
            }else {
                return DefaultCommandFactory.create(cex, keyword, args, sender);
            }
        }
    }

}

package ton.klay.wspro.core.game.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.commands.Command;
import ton.klay.wspro.core.game.commands.lowlevel.NextPhaseCommand;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Creates Comparators for commands based on different ranking types (e.g. INFO commands before RESPONSE commands)
 * This can be used to order them in terms of priority for execution.
 */
public class DefaultCommandComparator{

    private static final Logger log = LogManager.getLogger();

    private static final int LOW_PRIORITY = 1;
    private static final int NORMAL_PRIORITY = 5;
    private static final int HIGH_PRIORITY= 10;

    private static Map<String, Integer> keywordPriorityMap;

    static {
        keywordPriorityMap = new HashMap<>();
        //todo rank commands

        keywordPriorityMap.put(NextPhaseCommand.CMD_NAME, LOW_PRIORITY);



    }

    /**
     * Obtains the default comparator for commands
     * @return
     */
    public static Comparator<Command> obtain(){

        return new Comparator<Command>() {
            @Override
            public int compare(Command o1, Command o2) {
                return keywordPriorityMap.getOrDefault(o1.getKeyword(), NORMAL_PRIORITY) - keywordPriorityMap.getOrDefault(o2.getKeyword(), NORMAL_PRIORITY);
            }
        };
    }
}

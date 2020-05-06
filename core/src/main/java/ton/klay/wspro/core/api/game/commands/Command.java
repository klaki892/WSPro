package ton.klay.wspro.core.api.game.commands;


import ton.klay.wspro.core.api.game.communication.Communicable;
import ton.klay.wspro.core.api.game.setup.GameContext;

/**
 * Implementation of a command to obtain or do information in the game.<br/>
 * A command is the building block of all interactions and progression with a game. <br/>
 * Since commands execute arbitrary code, commands are also responsible for self managing the following:
 * <ul>
 *     <li>Issuing events that correspond to the command</li>
 *     <li>Issuing other commands as necessary to preform complex actions </li>
 *     <li>Logging what action this command preformed (optional but recommended for debugging)</li>
 *     <li>Knowing what to do to reverse it's own action via {@link CommandExecution#undo(GameContext, String[])}</li>
 * </ul>
 *
 * Because commands preform code that can break (and likely will) break the game, its advised to have a {@link CommandExecutor}
 * which can manage and catch exceptions that may happen as a result of a command being issued. <br/><br/>
 *
 * All commands should have a unique Keyword obtainable by {@link #getKeyword()} which allows for serialization of the command
 * for easier calling from any {@link CommandSender}
 *
 */
public interface Command extends Communicable {


    CommandExecution getExecution();

    String getKeyword();

    String[] getArgs();

        /**
         * Obtain information about who made this command
         * @return
         */
    CommandSender getSender();


}

package ton.klay.wspro.core.game.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.commands.CommandExecution;
import ton.klay.wspro.core.api.game.commands.CommandSender;
import ton.klay.wspro.core.api.game.commands.Command;
import ton.klay.wspro.core.api.scripting.ScriptEngine;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * A command factory which creates command instances, ready to be executed from various sources. <br/>
 * This Default version of a command factory can only create commands which have 0 parameters passed to it, as the factory
 * takes a class reference of a command and then creates an instance from that class reference. <br/> <br/>
 *
 *
 * For commands which come from compiled java classes, the command factory takes a reference of a class (which implements {@link CommandExecution}) and then attempts
 * to create a new object from the class reference. <br/> If it can't , it simply returns null catching any reflective class exceptions.
 * <br/><br/>
 * For commands which come from a scripted source: the command factory needs a reference to the {@link ScriptEngine} which created the
 * {@link CommandExecution}. This is required because if a {@link ScriptEngine} creates a {@link CommandExecution}, the JVM needs to know who to ask
 * to create it again. The factory then takes the knowledge from the JVM and creates a new instance. <br/>
 * How it works (Java Reflection topic):<br/>
 * Because the likely implementation of creating a {@link CommandExecution} from a {@link ScriptEngine} involves making an Anonymous class that implements {@link CommandExecution},
 * to create an Anonymous inner class you need to pass a reference of the parent class. (a hidden constructor argument for anonymous inner classes)<br/>
 * Thus, {@link #createFromScript(Class, String, String[], CommandSender, ScriptEngine)} exists to obtain the
 * {@link ScriptEngine} who knows how to remake the {@link CommandExecution} for the {@link Command}.
 * <br/>Sources to create this implementation:
 * <ul>
 *     <li><a href="https://docs.oracle.com/javase/tutorial/reflect/member/ctorInstance.html">Oracle Tutorial page on making new class instances programmatically.</a></li>
 *     <li><a href="https://stackoverflow.com/a/25634584"> Stack overflow: creating a new instance of an inner class programmatically</a> </li>
 * </ul>
 */
public class DefaultCommandFactory {

    private static final Logger log = LogManager.getLogger();

    protected DefaultCommandFactory(){}

    /**
     * Creates a instance of a command from a known, compiled, java class reference. <br/>
     *
     * @param cex The class of the execution which can be made
     * @param keyword The keyword this command is known by
     * @param argList a list of string arguments that the command may use to execute
     * @param sender the one who issued the command
     * @return an executable command, null if the {@link CommandExecution} from cex couldn't not be instantiated
     */
    public static Command create(Class<? extends CommandExecution> cex, String keyword, String[] argList, CommandSender sender){

        try{
            log.trace("Creating a new instance of a compiled CommandExecution class");
            CommandExecution instance = cex.newInstance();

            return new CommandImpl(instance, keyword, argList, sender);

        } catch (IllegalAccessException | InstantiationException e) {
            log.error("Error Creating Command: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * Creates a command from a script
     *
     * @param cex - the class of the execution
     * @param keyword The keyword this command is known by
     * @param argList a list of string arguments that the command may use to execute
     * @param sender the one who issued the command
     * @param scriptEngine the script engine which created the first parameter.
     * @return an executable command
     */
    public static Command createFromScript(Class<? extends  CommandExecution> cex, String keyword, String[] argList, CommandSender sender, ScriptEngine scriptEngine){
        CommandExecution instance = null;
        log.trace("Creating a new instance of a scripted CommandExecution class");

        Constructor<?>[] declaredConstructors = cex.getDeclaredConstructors();
        for (Constructor<?> constructor : declaredConstructors) {
            try {
                instance = (CommandExecution) constructor.newInstance(scriptEngine);
            } catch (ClassCastException | InstantiationException | IllegalAccessException | InvocationTargetException ignored) {}
        }

        if (instance != null)
            return new CommandImpl(instance, keyword, argList, sender);
        else
            return null;
    }

    private static class CommandImpl implements Command {

        private static final Logger log = LogManager.getLogger();
        private CommandExecution cex;
        private final String keyword;
        private final CommandSender sender;
        private String[] args;

        CommandImpl(CommandExecution cex, String keyword, String[] args, CommandSender sender){
            this.cex = cex;
            this.keyword = keyword;
            this.sender = sender;
            this.args = args;
        }

        @Override
        public String[] getArgs() {
            return args;
        }

        @Override
        public CommandExecution getExecution() {
            return cex;
        }

        @Override
        public String getKeyword() {
            return keyword;
        }

        @Override
        public CommandSender getSender() {
            return sender;
        }

        @Override
        public String toCommunicableString() {
            StringBuilder retString = new StringBuilder(keyword + " ");
            for (String para : args)
                retString.append(para).append(" ");
            return retString.toString().trim();
        }

        @Override
        public String toString() {
            return toCommunicableString();
        }
    }
}

package ton.klay.wspro.core.test.endtoend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.commands.Command;
import ton.klay.wspro.core.api.game.commands.CommandExecutionException;
import ton.klay.wspro.core.api.game.commands.CommandSender;
import ton.klay.wspro.core.api.game.commands.CommandSenderType;
import ton.klay.wspro.core.test.playground.TestScriptEngine;

import java.util.ArrayList;

public class TestCommandFlow {

    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args){

        CommandSender testCommandSender = new CommandSender() {
            @Override
            public String getName() {
                return "test command sender";
            }

            @Override
            public CommandSenderType getSenderType() {
                return CommandSenderType.PLAYER;
            }
        };


        //create a decoder that will handle messages
        DefaultWeissCommandDecoder decoder = new DefaultWeissCommandDecoder(new TestScriptEngine());

        ArrayList<Command> commands = new ArrayList<>();
        //decode a command
        Command command1 = decoder.decode(() -> "TEST ARG1 ARG2 ARG3", testCommandSender);
        commands.add(command1);

        //decode an unknown command (would be sent to a script engine to test for custom implementation
        Command command2 = decoder.decode(() -> "GAMESTART", testCommandSender);
        commands.add(command2);

        ;

        //simulate a command executor, execute a command.
        for (Command command : commands) {
            try {
                command.getExecution().execute(null, command.getArgs(), testCommandSender);
            } catch (CommandExecutionException e) {
                e.printStackTrace();
            }
        }

    }
}

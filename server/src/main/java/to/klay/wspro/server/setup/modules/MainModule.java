package to.klay.wspro.server.setup.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;
import to.klay.wspro.server.ServerMain;
import to.klay.wspro.server.setup.CommandLineArgumentOptions;

/**
 * Responsible for starting the program from a standard command line / IDE
 */
public class MainModule extends AbstractModule {

    private static final Logger log = LogManager.getLogger();

    ServerOptions options;

    public static void main(String[] args) {
        //pull configurations
        CommandLineArgumentOptions commandLineArgumentOptions = new CommandLineArgumentOptions();
        CommandLine.ParseResult parseResult = new CommandLine(commandLineArgumentOptions).parseArgs(args);
        if (parseResult.isUsageHelpRequested()){
            CommandLine.usage(commandLineArgumentOptions, System.out);
            System.exit(0);
        }

        ServerOptions serverOptions = new ServerOptions(commandLineArgumentOptions);
        Injector injector = Guice.createInjector(serverOptions);
        FinderModule finderModule = injector.getInstance(FinderModule.class);

        //injectors that need the starting configuration information to do thier job:
        Injector level2Injector = injector.createChildInjector(finderModule);

        //start server
        level2Injector.getInstance(ServerMain.class).startServer(finderModule.getCardSource(), finderModule.getAbilitySource());

    }

}

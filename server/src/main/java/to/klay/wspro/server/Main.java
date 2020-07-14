package to.klay.wspro.server;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;
import to.klay.wspro.server.setup.CommandLineArgumentOptions;
import to.klay.wspro.server.setup.modules.FinderModule;
import to.klay.wspro.server.setup.modules.ServerOptions;

/**
 * Responsible for starting the program from a standard command line / IDE
 */
public class Main {

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
        serverOptions.initConfig();
        new Main().injectAndStart(serverOptions);

    }

    protected Injector getServerInjector(ServerOptions serverOptions) {
        Injector injector = Guice.createInjector(serverOptions);
        FinderModule finderModule = injector.getInstance(FinderModule.class);

        //injectors that need the starting configuration information to do thier job:
        return injector.createChildInjector(finderModule);
    }

    protected void injectAndStart(ServerOptions serverOptions) {
        Injector level2Injector = getServerInjector(serverOptions);
        ServerGameManager manager = level2Injector.getInstance(ServerGameManager.class);
        //start server
        level2Injector.getInstance(ArmeriaServer.class).startServer(manager);
    }

}

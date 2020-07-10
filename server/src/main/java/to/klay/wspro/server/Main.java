package to.klay.wspro.server;

import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import picocli.CommandLine;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class Main {

    static {
//        StatusLogger.getLogger().setLevel(Level.DEBUG);
    }
    private static final Logger log = LogManager.getLogger();


    public static void main(String[] args) throws IOException {
        ServerOptions options = new ServerOptions();
        CommandLine.ParseResult parseResult = new CommandLine(options).parseArgs(args);
        if (parseResult.isUsageHelpRequested()){
            CommandLine.usage(options, System.out);
            System.exit(0);
        }


        setLoggerConfig(options);
        log.info("WSPro Server Starting");
        log.debug(parseResult.originalArgs());
        log.debug(options);
        //to do stuff based on configuration (start up additional services


        //start the server
        ServerBuilder sb = Server.builder();
        //configurations
        sb.http(options.port);
        sb.service("/", (ctx, req) -> HttpResponse.of("WSPro Server"));
        sb.accessLogWriter(AccessLogWriter.combined(), true);

        Server server = sb.build();
        CompletableFuture<Void> future = server.start();
        //wait for server to finish starting up
        future.join();
        log.info("server finished starting up");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Server Shutting down");
            server.stop();
        }));
    }

    private static void setLoggerConfig(ServerOptions options) {
        if (options.logLevel != null) {
            LoggerContext context = (LoggerContext) LogManager.getContext(false);
            Configuration config = context.getConfiguration();
            LoggerConfig loggerConfig = config.getLoggerConfig(LoggerConfig.ROOT);
            loggerConfig.setLevel(options.logLevel);
            context.updateLoggers();
        }
    }

}

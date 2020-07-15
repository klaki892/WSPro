package to.klay.wspro.server;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.linecorp.armeria.common.HttpHeaderNames;
import com.linecorp.armeria.common.HttpMethod;
import com.linecorp.armeria.common.HttpResponse;
import com.linecorp.armeria.common.grpc.GrpcSerializationFormats;
import com.linecorp.armeria.common.logging.LogLevel;
import com.linecorp.armeria.server.Server;
import com.linecorp.armeria.server.ServerBuilder;
import com.linecorp.armeria.server.cors.CorsService;
import com.linecorp.armeria.server.cors.CorsServiceBuilder;
import com.linecorp.armeria.server.grpc.GrpcService;
import com.linecorp.armeria.server.grpc.GrpcServiceBuilder;
import com.linecorp.armeria.server.logging.AccessLogWriter;
import com.linecorp.armeria.server.logging.LoggingService;
import io.grpc.protobuf.services.ProtoReflectionService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import to.klay.wspro.server.grpc.PlayWeissService;
import to.klay.wspro.server.grpc.SetupGameService;
import to.klay.wspro.server.setup.modules.ServerOptions;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ArmeriaServer {

    private static final Logger log = LogManager.getLogger();

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @Inject
    Optional<Level> logLevel;

    @Inject
    @Named(ServerOptions.PORT_KEY)
    int port;

    @Inject
    @Named(ServerOptions.ENABLE_DEBUGGING_KEY)
    boolean enableDebugging;

    @Inject
    @Named(ServerOptions.GAME_LIMIT_KEY)
    int gameLimit;



    public void startServer(ServerGameManager gameManager) {

        setLoggerConfig();
        log.info("WSPro Server Starting");
        //to do stuff based on configuration (start up additional services


        //start the server
        ServerBuilder sb = Server.builder();
        //configurations
        sb.http(port);
        sb.service("/", (ctx, req) -> HttpResponse.of("WSPro Server"));
        sb.accessLogWriter(AccessLogWriter.combined(), true);



        GrpcServiceBuilder grpcGameService = GrpcService.builder()
                .addService(new SetupGameService(gameManager))
                .addService(new PlayWeissService(gameManager));

        //support GRPC-WEB and JSON formats
        grpcGameService.supportedSerializationFormats(GrpcSerializationFormats.values());

        //handle CORS
        CorsServiceBuilder corsBuilder = CorsService.builderForAnyOrigin()
                .allowRequestMethods(HttpMethod.POST)
                .allowRequestHeaders(HttpHeaderNames.CONTENT_TYPE,
                        HttpHeaderNames.of("X-GRPC-WEB"))
                // Expose trailers of the HTTP response to the client.
                .exposeHeaders("Grpc-Status", "Grpc-Message", "Grpc-Encoding", "Grpc-Accept-Encoding");


        if (enableDebugging) {
            sb.decorator(LoggingService.builder()
                    .successfulResponseLogLevel(LogLevel.DEBUG)
                    .failureResponseLogLevel(LogLevel.ERROR)
                .newDecorator());
            grpcGameService.addService(ProtoReflectionService.newInstance());
            log.info("grpcGameService - Proto Reflection Service enabled");
        }



        //add services and build server
        sb.service(grpcGameService.build(), corsBuilder.newDecorator());

        Server server = sb.build();
        CompletableFuture<Void> future = server.start();
        //wait for server to finish starting up
        future.join();
        log.info("Server finished starting up - " + server.defaultHostname()+":"+server.activeLocalPort());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Server Shutting down");
            server.stop();
        }));
    }

    private void setLoggerConfig() {
        if (logLevel.isPresent()) {
            LoggerContext context = (LoggerContext) LogManager.getContext(false);
            Configuration config = context.getConfiguration();
            LoggerConfig loggerConfig = config.getLoggerConfig(LoggerConfig.ROOT);
            loggerConfig.setLevel(logLevel.get());
            context.updateLoggers();
        }
    }

}

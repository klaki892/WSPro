package to.klay.wspro.server.grpc.manual;

import com.google.inject.Injector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.server.Main;
import to.klay.wspro.server.ServerGameManager;
import to.klay.wspro.server.grpc.GrpcTestServer;

import java.io.IOException;
import java.net.URISyntaxException;

public class ManualTestServerStart extends Main {

    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args) {
        try {
            Injector injector = new ManualTestServerStart().getServerInjector(new PropertiesFileConfigModule());

            ServerGameManager serverGameManager = injector.getInstance(ServerGameManager.class);
            injector.getInstance(GrpcTestServer.class).startServer(serverGameManager);

        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}

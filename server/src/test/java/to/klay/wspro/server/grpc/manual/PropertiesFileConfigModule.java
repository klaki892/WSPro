package to.klay.wspro.server.grpc.manual;

import co.unruly.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.server.setup.modules.ServerOptions;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public class PropertiesFileConfigModule extends ServerOptions {

    private static final Logger log = LogManager.getLogger();


    public PropertiesFileConfigModule() throws URISyntaxException {
        URL resource = this.getClass().getClassLoader().getResource("server-config.properties");
        config = Configuration.from(Configuration.properties(new File(resource.toURI()).getAbsolutePath()));
    }

    @Override
    public void initConfig() {
    }
}

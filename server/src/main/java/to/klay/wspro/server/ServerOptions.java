package to.klay.wspro.server;

import com.google.common.base.MoreObjects;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

public class ServerOptions {

    private static final Logger log = LogManager.getLogger();

    @CommandLine.Option(
            names = {"-p", "--port"},
            defaultValue = "8080",
            description = "Port the server will run on.(default: ${DEFAULT-VALUE})"
    )
    int port;

    @CommandLine.Option(
            names = {"-h", "--help"},
            usageHelp = true,
            description = "Displays this help message"
    )
    boolean help;

    @CommandLine.Option(
            names = {"-v", "--log-level"},
            description = "logging verbosity (default: ${DEFAULT-VALUE}) Values (OFF, TRACE, DEBUG, INFO, WARN, ERROR, FATAL)",
            converter = levelTypeConverter.class
    )
    Level logLevel;

    //todo TLS options, health check endpoint, card DB info/format, ability info/format

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("port", port)
                .add("help", help)
                .add("logLevel", logLevel)
                .toString();
    }

    static class levelTypeConverter implements CommandLine.ITypeConverter<Level> {

        @Override
        public Level convert(String s) throws Exception {
            return Level.valueOf(s);
        }
    }
}

package to.klay.wspro.server.setup;

import co.unruly.config.ConfigurationSource;
import com.google.common.base.MoreObjects;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;
import to.klay.wspro.server.setup.modules.ServerOptions;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static to.klay.wspro.server.setup.modules.ServerOptions.DEFAULT_GAME_LIMIT;

public class CommandLineArgumentOptions implements ConfigurationSource {

    private static final Logger log = LogManager.getLogger();

    Map<String, String> configMappings;
    public CommandLineArgumentOptions(){
        configMappings = new HashMap<>();
    }

    @CommandLine.Option(
            names = {"-p", "--port"},
            description = "Port the server will run on.(default: "+ ServerOptions.DEFAULT_PORT+")"
    )
    private int port;

    @CommandLine.Option(
            names = {"-h", "--help"},
            usageHelp = true,
            description = "Displays this help message"
    )
    private boolean help;

    @CommandLine.Option(
            names = {"-v", "--log-level"},
            description = "logging verbosity (default: ${DEFAULT-VALUE}) Values (OFF, TRACE, DEBUG, INFO, WARN, ERROR, FATAL)",
            converter = levelTypeConverter.class
    )
    private Level logLevel;

    @CommandLine.Option(
            names = {"-d", "--debug"},
            description = "Enables debugging capabilities. Activates GRPC Server Reflection"
    )
    private boolean enableDebugging;

    @CommandLine.Option(
            names = {"--max-games"},
            description = "The limit for how many games can be run by this server at once. (default: "+ DEFAULT_GAME_LIMIT + ")"
    )
    private int gameLimit;

    //todo TLS options, health check endpoint, card DB info/format, ability info/format

    @CommandLine.Option(
            names = {"--card-source"},
            description = "The Type of Source to retreive card information. " +
                    "(Default: ${DEFAULT-VALUE}) " +
                    "(Values: ${COMPLETION-CANDIDATES)}",
            defaultValue = "DATABASE"


    )
    private AbilityFinderSourceType cardSource;
    private AbilityFinderSourceType abilitySource;

    @CommandLine.Option(
            names = {"--properties"},
            description = "Looks for arguments not defined at command line in a .properties file. "

    )
    private File propertiesFile;

    
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("port", getPort())
                .add("help", isHelp())
                .add("logLevel", getLogLevel())
                .toString();
    }

    @Override
    public String get(String s) {
        return configMappings.get(s);
    }

    public void populateMap(){
        configMappings = new HashMap<>();
        configMappings.put(ServerOptions.PORT_KEY, String.valueOf(getPort()));
        configMappings.put(ServerOptions.LOG_LEVEL_KEY, String.valueOf(getLogLevel()));
        configMappings.put(ServerOptions.ENABLE_DEBUGGING_KEY, String.valueOf(enableDebugging()));
        configMappings.put(ServerOptions.GAME_LIMIT_KEY, String.valueOf(getGameLimit()));
        configMappings.put(ServerOptions.CARD_SOURCE_TYPE_KEY, String.valueOf(getCardSource()));
        configMappings.put(ServerOptions.ABILITY_SOURCE_TYPE_KEY, String.valueOf(getAbilitySource()));
        configMappings.put(ServerOptions.CONFIG_FILE, propertiesFile.getAbsolutePath());
    }

    static class levelTypeConverter implements CommandLine.ITypeConverter<Level> {

        
        public Level convert(String s) throws Exception {
            return Level.valueOf(s);
        }
    }

    
    public int getPort() {
        return port;
    }

    
    public boolean isHelp() {
        return help;
    }

    
    public Level getLogLevel() {
        return logLevel;
    }

    
    public boolean enableDebugging() {
        return enableDebugging;
    }

    
    public int getGameLimit() {
        return gameLimit;
    }

    
    public AbilityFinderSourceType getCardSource() {
        return cardSource;
    }

    
    public AbilityFinderSourceType getAbilitySource() {
        return abilitySource;
    }

    public File getPropertiesFile() {
        return propertiesFile;
    }
}

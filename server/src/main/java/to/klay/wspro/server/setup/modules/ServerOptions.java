package to.klay.wspro.server.setup.modules;

import co.unruly.config.Configuration;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.server.setup.AbilityFinderSourceType;
import to.klay.wspro.server.setup.CardFinderSourceType;
import to.klay.wspro.server.setup.CommandLineArgumentOptions;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Properties;

public class ServerOptions extends AbstractModule {


    public static final String DEFAULT_PORT                         = "8080";
    public static final String DEFAULT_GAME_LIMIT                   = "1";
    public static final String CONFIG_FILE                          = "wspro.server.config";
    public static final String ENABLE_DEBUGGING_KEY                 = "wspro.server.debug";
    public static final String LOG_LEVEL_KEY                        = "wspro.server.loglevel";
    public static final String GAME_LIMIT_KEY                       = "wspro.server.gamelimit";
    public static final String CARD_SOURCE_TYPE_KEY                 = "wspro.server.card.source.type";
    public static final String CARD_SOURCE_DIRECTORY_PATH_KEY       = "wspro.server.card.directory";
    public static final String ABILITY_SOURCE_TYPE_KEY              = "wspro.server.ability.source.type";
    public static final String ABILITY_SOURCE_DIRECTORY_PATH_KEY    = "wspro.server.ability.directory";
    public static final String PORT_KEY                             = "wspro.server.port" ;
    private static final Logger log = LogManager.getLogger();

    private Configuration config;

    public ServerOptions(CommandLineArgumentOptions commandLineArgumentOptions){

        config = null;

        commandLineArgumentOptions.populateMap();
        if (commandLineArgumentOptions.getPropertiesFile().exists()){
            Properties propertiesFile = new Properties();
            try {
                propertiesFile.load(new FileReader(commandLineArgumentOptions.getPropertiesFile()));
                config = Configuration.from(Configuration.properties(propertiesFile));
            } catch (RuntimeException | IOException ex0){
                log.error(ex0);
                config = null;
            }
        }

        //enable command line, then environment argument retireval
        if (config == null) {
            config = Configuration.from(commandLineArgumentOptions);
        } else{
            config.or(commandLineArgumentOptions);
        }
        //environment variable search
        config.or((key -> System.getenv(key.replace(".", "_")).toLowerCase()));

    }
    @Provides
    @Named(PORT_KEY)
    @Singleton
    public int getPort(){
       return Integer.parseInt(config.get(PORT_KEY,DEFAULT_PORT));
    }

    @Provides
    @Singleton
    public Optional<Level> getLogLevel(){
        if (config.get(LOG_LEVEL_KEY).isPresent()){
            return Optional.of(Level.valueOf(config.get(LOG_LEVEL_KEY).get()));
        } else {
            return Optional.empty();
        }
    }

    @Provides
    @Named(ENABLE_DEBUGGING_KEY)
    @Singleton
    public boolean enableDebugging(){
        return Boolean.parseBoolean(config.get(ENABLE_DEBUGGING_KEY, "false"));
    };

    @Provides
    @Named(GAME_LIMIT_KEY)
    @Singleton
    public int getGameLimit(){
     return Integer.parseInt(config.get(GAME_LIMIT_KEY, DEFAULT_GAME_LIMIT));
    }

    @Provides
    @Singleton
    public CardFinderSourceType getCardSource(){
        try {
            return CardFinderSourceType.valueOf(config.get(CARD_SOURCE_TYPE_KEY).get());
        } catch (IllegalArgumentException | NoSuchElementException ex){
            RuntimeException descriptive =
                    new IllegalArgumentException("Invalid or non-existent Card-finding source selected", ex);
            log.fatal(descriptive);
            throw descriptive;
        }
    }

    @Provides
    @Named(CARD_SOURCE_DIRECTORY_PATH_KEY)
    @Singleton
    public Path getCardSourceDirectory(){
        return new File(config.get(CARD_SOURCE_DIRECTORY_PATH_KEY).get()).toPath();
    }
    @Provides
    @Singleton
    public AbilityFinderSourceType getAbilitySource(){
        try {
            return AbilityFinderSourceType.valueOf(config.get(ABILITY_SOURCE_TYPE_KEY).get());
        } catch (IllegalArgumentException | NoSuchElementException ex){
            RuntimeException descriptive =
                    new IllegalArgumentException("Invalid or non-existent Ability-finding source selected", ex);
            log.fatal(descriptive);
            throw descriptive;
        }
    }

    @Provides
    @Named(ABILITY_SOURCE_DIRECTORY_PATH_KEY)
    @Singleton
    public Path getAbilitySourceDirectory(){
        return new File(config.get(ABILITY_SOURCE_DIRECTORY_PATH_KEY).get()).toPath();
    }
}

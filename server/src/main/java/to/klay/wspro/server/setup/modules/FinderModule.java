package to.klay.wspro.server.setup.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.server.setup.AbilityFinderSourceType;
import to.klay.wspro.server.setup.CardFinderSourceType;
import to.klay.wspro.server.setup.finders.abilities.DirectoryAbilityFinder;
import to.klay.wspro.server.setup.finders.abilities.QueryableAbilityFinder;
import to.klay.wspro.server.setup.finders.cards.CardFinder;
import to.klay.wspro.server.setup.finders.cards.wsengdb.WsEngDbCardFinder;

import java.nio.file.Path;


public class FinderModule extends AbstractModule {

    private static final Logger log = LogManager.getLogger();
    private final CardFinderSourceType cardFinderSourceType;
    private final AbilityFinderSourceType abilityFinderSourceType;
    private final Path cardPath;
    private final Path abilityPath;

    @Inject
    public FinderModule(CardFinderSourceType cardFinderSourceType,
                        AbilityFinderSourceType abilityFinderSourceType,
                        @Named(ServerOptions.CARD_SOURCE_DIRECTORY_PATH_KEY) Path CardPath,
                        @Named(ServerOptions.ABILITY_SOURCE_DIRECTORY_PATH_KEY) Path AbilityPath){

        this.cardFinderSourceType = cardFinderSourceType;
        this.abilityFinderSourceType = abilityFinderSourceType;
        cardPath = CardPath;
        abilityPath = AbilityPath;
    }


    @Provides
    @Singleton
    public CardFinder getCardSource(){

        CardFinder finder = null;
        switch (cardFinderSourceType){

            case WS_ENG_DB:
                return new WsEngDbCardFinder(cardPath);//todo ???
            case DATABASE:
                break; //todo
            case JSON:
                break; //todo
            default:
                throw new UnsupportedOperationException(
                        "Card-finder not supported: " + cardFinderSourceType);
        }
        return finder;

    }


    @Provides
    @Singleton
    public QueryableAbilityFinder getAbilitySource(){


        QueryableAbilityFinder finder = null;

        switch (abilityFinderSourceType){

            case DIRECTORY:
                return new DirectoryAbilityFinder(abilityPath);
            case DATABASE:
                break;
            case JSON:
                break;
            default:
                IllegalStateException ex = new IllegalStateException("Unexpected value: " + abilityFinderSourceType);
                log.error(ex);
                throw ex;
        }

        return finder;
    }

}

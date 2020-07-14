package to.klay.wspro.core.game.formats.standard;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.game.DeckConstructionFormats;
import to.klay.wspro.core.api.game.phase.GamePhase;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.api.game.setup.GameFormat;
import to.klay.wspro.core.api.game.setup.GameFormats;
import to.klay.wspro.core.api.game.setup.GameLocale;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The standard weiss format, providing a basis for the standard gameplay covered in the Comprehensive rules<br/>
 * Includes Standard Details & Restrictions such as:
 * <ul>
 *     <li>50 Card Decks</li>
 *     <li>Play Zones as defined in the Comprehensive Rules</li>
 * </ul>
 *
 */
public class StandardWeissFormat implements GameFormat {

    private static final Logger log = LogManager.getLogger();
    private static final GameFormats formatName = GameFormats.STANDARD;
    private static final int PLAYER_COUNT = 2;
    private Map<String, GamePhase> phaseMap;

    private GameLocale gameLocale;
    private DeckConstructionFormats deckFormat;


    public StandardWeissFormat(GameLocale locale, DeckConstructionFormats deckFormat){
        this.gameLocale  = locale;
        this.deckFormat = deckFormat;
        
        this.phaseMap = initPhaseMap();
    }

    private Map<String, GamePhase> initPhaseMap() {
        Map<String, GamePhase> retMap = new HashMap<>();

        return retMap;
    }


    @Override
    public GameFormats getName() {
        return formatName;
    }

    @Override
    public void setGameLocale(GameLocale gameLocale) {
        this.gameLocale = gameLocale;
    }

    @Override
    public GameLocale getGameLocale() {
        return gameLocale;
    }

    @Override
    public boolean requirementsMet(Collection<GamePlayer> players) {

        return playerRestrictionMet(players) && deckRestrictionsMet(players);

    }

    private boolean playerRestrictionMet(Collection<GamePlayer> players) {
        return players.size() == PLAYER_COUNT;
    }


    //Checks the Player's decks against the current Deck Construction format
    private boolean deckRestrictionsMet(Collection<GamePlayer> players) {
        //todo defer deck checking to a utility method which can probably do it.
        for (GamePlayer player : players) {
//            DeckUtils.CheckDeckRestrictions
        }
        return true;
    }

    @Override
    public Collection<String> getErrors() {
        return null;
    }

    @Override
    public GamePhase getPhase(String phaseName) {
        return phaseMap.get(phaseName);
    }

    @Override
    public String getFormatInfo() {
        return null;
    }
}

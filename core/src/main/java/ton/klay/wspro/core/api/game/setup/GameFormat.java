package ton.klay.wspro.core.api.game.setup;

import ton.klay.wspro.core.api.game.DeckConstructionFormats;
import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.api.game.phase.GamePhase;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.setup.GameFormats;
import ton.klay.wspro.core.api.game.setup.GameLocale;

import java.util.Collection;
import java.util.List;

/**
 * The Ruleset in which the game follows. <br/>
 * Provides a means of interacting with parameters of the game such as {@link GameLocale},
 * and {@link DeckConstructionFormats}
 */
public interface GameFormat {

    GameFormats getName();

    void setGameLocale(GameLocale gameLocale);

    GameLocale getGameLocale();

    /**
     * Returns true if all format restrictions have been met to start a game.
     *
     * @param players - A list of players to check that each one meets the game's requirements
     * @return True - restrictions have been met
     */
    boolean requirementsMet(Collection<GamePlayer> players);

    /**
     * Returns a list of errors that prevent the game from starting
     * due to restrictions on the game not being met.
     *
     * @return
     */
    Collection<String> getErrors();

    /**
     * Obtains the start phase which will start off the main execution of the game.
     * Ideally, implementations should point this to a startup {@link GamePhase}. <br/>
     * Typically this includes setting up the player's game field, initializing all of the zones and maybe doing extra steps
     * such as placing the deck into the Deck zone, this can also be done during another step of the format. <br/>
     * @return
     */
    GamePhase getStartPhase();

    GamePhase getPhase(String phaseName);

    String getFormatInfo();


}

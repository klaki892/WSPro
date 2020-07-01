package ton.klay.wspro.core.game.formats.standard.zones;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.cards.GameVisibility;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.field.Zones;

/**
 * AN object that represents the deck zone on a playing field.
 * //todo rules reference
 */
public class DeckZone extends MultiCardZone {

    private static final Logger log = LogManager.getLogger();

    private static final Zones ZONE_NAME = Zones.ZONE_DECK;


    public DeckZone(GamePlayer owner){
        super(owner, ZONE_NAME, GameVisibility.HIDDEN);
    }
}

package ton.klay.wspro.core.api.cards;

import ton.klay.wspro.core.api.game.setup.GameLocale;

/**
 * Grouping that holds a string based on the {@link GameLocale} associated with it
 */
public interface LocalizedString {
    GameLocale getLocale();
    String getString();
}

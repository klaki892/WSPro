package ton.klay.wspro.core.api.cards;

import ton.klay.wspro.core.api.game.setup.GameLocale;

/**
 * Grouping that holds a string based on the {@link GameLocale} associated with it
 */
public interface LocalizedString {
    GameLocale getLocale();
    String getString();

    static LocalizedString makeEN(String enString){
        return new LocalizedString() {
            @Override
            public GameLocale getLocale() {
                return GameLocale.EN;
            }

            @Override
            public String getString() {
                return enString;
            }

            @Override
            public String toString() {
                return getString();
            }
        };
    }
}

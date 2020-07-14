package ton.klay.wspro.core.api.cards;

import ton.klay.wspro.core.api.game.setup.GameLocale;

/**
 * Grouping that holds a string based on the {@link GameLocale} associated with it
 */
public class LocalizedString {

    GameLocale gameLocale;
    String string;

    protected LocalizedString(GameLocale gameLocale, String string){}

    public GameLocale getLocale(){
        return gameLocale;
    }
    public String getString(){
        return string; //todo concatenate both (or more) names
    }

    @Override
    public String toString(){
        return string;
    }

    public static LocalizedString makeEN(String enString){
        return new LocalizedString(GameLocale.EN, enString);
    }
}

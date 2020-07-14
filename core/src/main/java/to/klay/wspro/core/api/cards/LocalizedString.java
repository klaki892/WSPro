package to.klay.wspro.core.api.cards;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import to.klay.wspro.core.api.game.setup.GameLocale;
import to.klay.wspro.core.game.proto.GameLocaleProtoTypeConverter;
import to.klay.wspro.core.game.proto.ProtoLocalizedString;

/**
 * Grouping that holds a string based on the {@link GameLocale} associated with it
 */
@ProtoClass(ProtoLocalizedString.class)
public class LocalizedString {

    @ProtoField(converter = GameLocaleProtoTypeConverter.class)
    GameLocale locale;
    @ProtoField
    String string;

    protected LocalizedString(GameLocale locale, String string){
        this.locale = locale;
        this.string = string;
    }

    public GameLocale getLocale(){
        return locale;
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

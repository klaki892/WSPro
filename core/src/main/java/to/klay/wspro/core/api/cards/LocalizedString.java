/*******************************************************************************
 *  WSPro - Trading Card Game Simulator
 *  Copyright (C) 2020  Klayton Killough
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published
 *  by the Free Software Foundation, version 3 of the License.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

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

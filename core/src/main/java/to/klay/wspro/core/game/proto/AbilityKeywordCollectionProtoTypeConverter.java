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

package to.klay.wspro.core.game.proto;

import net.badata.protobuf.converter.type.TypeConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.abilities.AbilityKeyword;

import java.util.Collection;
import java.util.stream.Collectors;

public class AbilityKeywordCollectionProtoTypeConverter implements TypeConverter<Collection<AbilityKeyword>, Collection<ProtoAbilityKeyword>> {

    private static final Logger log = LogManager.getLogger();

    AbilityKeywordProtoTypeConverter protoTypeConverter = new AbilityKeywordProtoTypeConverter();

    @Override
    public Collection<AbilityKeyword> toDomainValue(Object instance) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<ProtoAbilityKeyword> toProtobufValue(Object instance) {
        Collection<AbilityKeyword> abilityKeywords = (Collection<AbilityKeyword>) instance;
        return abilityKeywords.stream().map(protoTypeConverter::toProtobufValue).collect(Collectors.toList());
    }
}

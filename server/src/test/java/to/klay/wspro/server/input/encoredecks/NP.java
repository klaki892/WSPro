
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

package to.klay.wspro.server.input.encoredecks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class NP {

    @SerializedName("attributes")
    @Expose
    private List<Object> attributes = null;
    @SerializedName("ability")
    @Expose
    private List<Object> ability = null;

    public List<Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Object> attributes) {
        this.attributes = attributes;
    }

    public List<Object> getAbility() {
        return ability;
    }

    public void setAbility(List<Object> ability) {
        this.ability = ability;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(attributes).append(ability).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof NP) == false) {
            return false;
        }
        NP rhs = ((NP) other);
        return new EqualsBuilder().append(attributes, rhs.attributes).append(ability, rhs.ability).isEquals();
    }

}

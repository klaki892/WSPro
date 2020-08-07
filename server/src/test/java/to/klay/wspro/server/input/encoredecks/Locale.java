
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

public class Locale {

    @SerializedName("EN")
    @Expose
    private EN eN;
    @SerializedName("NP")
    @Expose
    private NP nP;

    public EN getEN() {
        return eN;
    }

    public void setEN(EN eN) {
        this.eN = eN;
    }

    public NP getNP() {
        return nP;
    }

    public void setNP(NP nP) {
        this.nP = nP;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(eN).append(nP).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Locale) == false) {
            return false;
        }
        Locale rhs = ((Locale) other);
        return new EqualsBuilder().append(eN, rhs.eN).append(nP, rhs.nP).isEquals();
    }

}


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

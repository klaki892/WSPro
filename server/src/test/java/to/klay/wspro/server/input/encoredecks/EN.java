
package to.klay.wspro.server.input.encoredecks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class EN {

    @SerializedName("attributes")
    @Expose
    private List<String> attributes = null;
    @SerializedName("ability")
    @Expose
    private List<String> ability = null;
    @SerializedName("name")
    @Expose
    private String name;

    public List<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<String> attributes) {
        this.attributes = attributes;
    }

    public List<String> getAbility() {
        return ability;
    }

    public void setAbility(List<String> ability) {
        this.ability = ability;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(attributes).append(ability).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof EN) == false) {
            return false;
        }
        EN rhs = ((EN) other);
        return new EqualsBuilder().append(name, rhs.name).append(attributes, rhs.attributes).append(ability, rhs.ability).isEquals();
    }

}

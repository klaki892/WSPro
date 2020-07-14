
package to.klay.wspro.server.input.encoredecks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Set {

    @SerializedName("enabled")
    @Expose
    private Boolean enabled;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("set")
    @Expose
    private String set;
    @SerializedName("side")
    @Expose
    private String side;
    @SerializedName("release")
    @Expose
    private String release;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("hash")
    @Expose
    private String hash;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(side).append(set).append(release).append(name).append(id).append(lang).append(enabled).append(hash).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Set) == false) {
            return false;
        }
        Set rhs = ((Set) other);
        return new EqualsBuilder().append(side, rhs.side).append(set, rhs.set).append(release, rhs.release).append(name, rhs.name).append(id, rhs.id).append(lang, rhs.lang).append(enabled, rhs.enabled).append(hash, rhs.hash).isEquals();
    }

}

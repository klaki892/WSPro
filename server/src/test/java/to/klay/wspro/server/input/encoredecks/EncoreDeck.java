
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

public class EncoreDeck {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("valid")
    @Expose
    private Boolean valid;
    @SerializedName("cards")
    @Expose
    private List<EncoreCard> encoreCards = null;
    @SerializedName("sets")
    @Expose
    private List<Set> sets = null;
    @SerializedName("neo_fail")
    @Expose
    private Object neoFail;
    @SerializedName("views")
    @Expose
    private Integer views;
    @SerializedName("attributes")
    @Expose
    private List<Object> attributes = null;
    @SerializedName("favoritecount")
    @Expose
    private Integer favoritecount;
    @SerializedName("myfavorite")
    @Expose
    private Boolean myfavorite;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("userid")
    @Expose
    private Userid userid;
    @SerializedName("deckid")
    @Expose
    private String deckid;
    @SerializedName("datemodified")
    @Expose
    private String datemodified;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public List<EncoreCard> getCards() {
        return encoreCards;
    }

    public void setCards(List<EncoreCard> encoreCards) {
        this.encoreCards = encoreCards;
    }

    public List<Set> getSets() {
        return sets;
    }

    public void setSets(List<Set> sets) {
        this.sets = sets;
    }

    public Object getNeoFail() {
        return neoFail;
    }

    public void setNeoFail(Object neoFail) {
        this.neoFail = neoFail;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public List<Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Object> attributes) {
        this.attributes = attributes;
    }

    public Integer getFavoritecount() {
        return favoritecount;
    }

    public void setFavoritecount(Integer favoritecount) {
        this.favoritecount = favoritecount;
    }

    public Boolean getMyfavorite() {
        return myfavorite;
    }

    public void setMyfavorite(Boolean myfavorite) {
        this.myfavorite = myfavorite;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Userid getUserid() {
        return userid;
    }

    public void setUserid(Userid userid) {
        this.userid = userid;
    }

    public String getDeckid() {
        return deckid;
    }

    public void setDeckid(String deckid) {
        this.deckid = deckid;
    }

    public String getDatemodified() {
        return datemodified;
    }

    public void setDatemodified(String datemodified) {
        this.datemodified = datemodified;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(encoreCards).append(sets).append(deckid).append(description).append(userid).append(valid).append(neoFail).append(datemodified).append(myfavorite).append(name).append(attributes).append(favoritecount).append(views).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof EncoreDeck) == false) {
            return false;
        }
        EncoreDeck rhs = ((EncoreDeck) other);
        return new EqualsBuilder().append(encoreCards, rhs.encoreCards).append(sets, rhs.sets).append(deckid, rhs.deckid).append(description, rhs.description).append(userid, rhs.userid).append(valid, rhs.valid).append(neoFail, rhs.neoFail).append(datemodified, rhs.datemodified).append(myfavorite, rhs.myfavorite).append(name, rhs.name).append(attributes, rhs.attributes).append(favoritecount, rhs.favoritecount).append(views, rhs.views).isEquals();
    }

}

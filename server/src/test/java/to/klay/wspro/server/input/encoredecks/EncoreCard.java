
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

public class EncoreCard {

    @SerializedName("locale")
    @Expose
    private Locale locale;
    @SerializedName("series")
    @Expose
    private String series;
    @SerializedName("equivilantcard")
    @Expose
    private Object equivilantcard;
    @SerializedName("trigger")
    @Expose
    private List<String> trigger = null;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("attributes")
    @Expose
    private List<String> attributes = null;
    @SerializedName("ability")
    @Expose
    private List<String> ability = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("rarity")
    @Expose
    private String rarity;
    @SerializedName("side")
    @Expose
    private String side;
    @SerializedName("level")
    @Expose
    private Integer level;
    @SerializedName("cost")
    @Expose
    private Integer cost;
    @SerializedName("power")
    @Expose
    private Integer power;
    @SerializedName("soul")
    @Expose
    private Integer soul;
    @SerializedName("set")
    @Expose
    private String set;
    @SerializedName("release")
    @Expose
    private String release;
    @SerializedName("sid")
    @Expose
    private String sid;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("cardtype")
    @Expose
    private String cardtype;
    @SerializedName("colour")
    @Expose
    private String colour;
    @SerializedName("__v")
    @Expose
    private Integer v;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public Object getEquivilantcard() {
        return equivilantcard;
    }

    public void setEquivilantcard(Object equivilantcard) {
        this.equivilantcard = equivilantcard;
    }

    public List<String> getTrigger() {
        return trigger;
    }

    public void setTrigger(List<String> trigger) {
        this.trigger = trigger;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getSoul() {
        return soul;
    }

    public void setSoul(Integer soul) {
        this.soul = soul;
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCardtype() {
        return cardtype;
    }

    public void setCardtype(String cardtype) {
        this.cardtype = cardtype;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(side).append(cost).append(set).append(level).append(soul).append(release).append(trigger).append(locale).append(sid).append(colour).append(series).append(v).append(name).append(attributes).append(id).append(ability).append(power).append(cardtype).append(lang).append(equivilantcard).append(rarity).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof EncoreCard) == false) {
            return false;
        }
        EncoreCard rhs = ((EncoreCard) other);
        return new EqualsBuilder().append(side, rhs.side).append(cost, rhs.cost).append(set, rhs.set).append(level, rhs.level).append(soul, rhs.soul).append(release, rhs.release).append(trigger, rhs.trigger).append(locale, rhs.locale).append(sid, rhs.sid).append(colour, rhs.colour).append(series, rhs.series).append(v, rhs.v).append(name, rhs.name).append(attributes, rhs.attributes).append(id, rhs.id).append(ability, rhs.ability).append(power, rhs.power).append(cardtype, rhs.cardtype).append(lang, rhs.lang).append(equivilantcard, rhs.equivilantcard).append(rarity, rhs.rarity).isEquals();
    }

    public String getAdjustedID(){
        // not accurate for first 5~ish ENG releases sets.
        return set + "/" + side + release + (!side.isEmpty() && !release.isEmpty() ? '-' : "") + sid;
    }

}

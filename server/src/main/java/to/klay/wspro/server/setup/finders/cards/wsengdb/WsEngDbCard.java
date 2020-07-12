
package to.klay.wspro.server.setup.finders.cards.wsengdb;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

class WsEngDbCard {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("rarity")
    @Expose
    private String rarity;
    @SerializedName("expansion")
    @Expose
    private String expansion;
    @SerializedName("side")
    @Expose
    private String side;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("level")
    @Expose
    private String level;
    @SerializedName("cost")
    @Expose
    private String cost;
    @SerializedName("power")
    @Expose
    private String power;
    @SerializedName("soul")
    @Expose
    private Integer soul;
    @SerializedName("trigger")
    @Expose
    private List<String> trigger = new ArrayList<String>();
    @SerializedName("attributes")
    @Expose
    private List<String> attributes = new ArrayList<String>();
    @SerializedName("ability")
    @Expose
    private List<String> ability = new ArrayList<String>();
    @SerializedName("flavor_text")
    @Expose
    private String flavorText;
    @SerializedName("set")
    @Expose
    private String set;
    @SerializedName("release")
    @Expose
    private String release;
    @SerializedName("sid")
    @Expose
    private String sid;
    @SerializedName("image")
    @Expose
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getExpansion() {
        return expansion;
    }

    public void setExpansion(String expansion) {
        this.expansion = expansion;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public Integer getSoul() {
        return soul;
    }

    public void setSoul(Integer soul) {
        this.soul = soul;
    }

    public List<String> getTrigger() {
        return trigger;
    }

    public void setTrigger(List<String> trigger) {
        this.trigger = trigger;
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

    public String getFlavorText() {
        return flavorText;
    }

    public void setFlavorText(String flavorText) {
        this.flavorText = flavorText;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
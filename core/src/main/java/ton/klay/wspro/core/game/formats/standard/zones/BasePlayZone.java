package ton.klay.wspro.core.game.formats.standard.zones;

import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.api.game.field.Zones;

/**
 * An object that represents a zone thats interactable in the game. <br>
 * As of the offical rules, there are 13 offical zones
 * @see Zones
 * @see <code>Weiss Schwarz Rule 3</code>
 */
public abstract class BasePlayZone implements PlayZone {

    protected GamePlayer owner;
    protected Zones ZONE_NAME = null;
    protected boolean hiddenZone = false;

    public BasePlayZone(GamePlayer owner, Zones zoneName, boolean isHiddenZone){
        this.owner = owner;
        this.ZONE_NAME = zoneName;
        this.hiddenZone = isHiddenZone;
        //FIXME: it's possible we need an actual enum for hidden zone designation since there's hidden to both players, opposing, or not at all
    }


    @Override
    public String getZoneName(){
        return ZONE_NAME.name();
    }

    @Override
    public boolean isHiddenZone(){
        return hiddenZone;
    }

    @Override
    public GamePlayer getOwner() {
        return owner;
    }


    @Override
    public String toString() {
        return ZONE_NAME.toString();
    }
}

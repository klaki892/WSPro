package ton.klay.wspro.core.game.formats.standard.zones;

import ton.klay.wspro.core.api.cards.GameVisibility;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;

/**
 * An object that represents a zone thats interactable in the game. <br>
 * As of the offical rules, there are 13 offical zones
 * @see Zones
 * @see <code>Weiss Schwarz Rule 3</code>
 */
public abstract class BasePlayZone implements PlayZone {

    protected GamePlayer owner;
    protected Zones ZONE_NAME = null;
    protected GameVisibility visibility;

    public BasePlayZone(GamePlayer owner, Zones zoneName, GameVisibility visibility){
        this.owner = owner;
        this.ZONE_NAME = zoneName;
        this.visibility = visibility;
    }


    public Zones getZoneName(){
        return ZONE_NAME;
    }

    @Override
    public GameVisibility getVisibility() {
        return visibility;
    }

    @Override
    public GamePlayer getOwner() {
        return owner;
    }

    @Override
    public GamePlayer getMaster() {
        return getOwner();
    }

    @Override
    public String toString() {
        return ZONE_NAME.toString();
    }
}

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

package to.klay.wspro.core.game.formats.standard.zones;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.GameVisibility;
import to.klay.wspro.core.api.game.GameEntity;
import to.klay.wspro.core.api.game.field.Zones;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.proto.GameVisibilityTypeConverter;
import to.klay.wspro.core.game.proto.ProtoPlayZone;
import to.klay.wspro.core.game.proto.ZonesProtoTypeConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * A zone that could be represented in a list format for functionality.
 */
@ProtoClass(ProtoPlayZone.class)
public class PlayZone implements GameEntity {

    private static final Logger log = LogManager.getLogger();
    @ProtoField
    protected GamePlayer owner;
    @ProtoField(converter = ZonesProtoTypeConverter.class)
    protected Zones zoneName;
    @ProtoField(converter = GameVisibilityTypeConverter.class)
    protected GameVisibility visibility;

    @ProtoField
    ArrayList<PlayingCard> contents = new ArrayList<>();

    /**
     * Creates a zone with a list based implementation for holding the cards within it.
     * @param zoneName - the name of the zone for referencing and searching
     * @param visibility  - indicates who is allowed to see the information within the zone.
     */
    public PlayZone(GamePlayer owner, Zones zoneName, GameVisibility visibility){
        this.owner = owner;
        this.zoneName = zoneName;
        this.visibility = visibility;
    }

    protected ArrayList<PlayingCard> getCardList() {
        return contents;
    }

    /**
         * Adds a single card to the zone
         * @param card - the card to be added into the zone
         */
    public void add(PlayingCard card) {
        getCardList().add(card);
    }

    public void add(PlayingCard card, int index) {
        getCardList().add(index, card);
    }

    /**
     * Adds multiple cards to the card List<br>
     * @param cards - array of cards to be added to the zone (order is not significant)
     */
    public void add(PlayingCard[] cards) {
        for (PlayingCard card : cards)
            this.add(card);

    }


    /**
         * Removes the specific card from the zone
         * @param card - card inside the zone to be removed
         */
    public void remove(PlayingCard card) {
         getCardList().remove(card);
    }

    /**
         * Checks if a card exists inside the zone
         * @param card - the card to check for existance in the zone.
         * @return Whether the card exists in the zone
         */
    public boolean contains(PlayingCard card) {
        return getCardList().contains(card);
    }

    /**
         * Returns the number of cards inside the zone
         * @return - the number of cards currently in the zone
         */
    public int size() {
        return getCardList().size();
    }

    /**
         * Returns an array of all the cards that were in the zone in the order they were stored.
         * @return an array containing all of the cards in the zone
         */
    public List<PlayingCard> getContents() {
        return new ArrayList<>(getCardList());
    }

    public Zones getZoneName(){
        return zoneName;
    }

    public GameVisibility getVisibility() {
        return visibility;
    }

    /**
         * Returns the owner of this zone
         * @return The player that controls this zone.
         */
    public GamePlayer getOwner() {
        return owner;
    }

    @Override
    public GamePlayer getMaster() {
        return getOwner();
    }

    @Override
    public String toString() {
        return zoneName.toString();
    }
}

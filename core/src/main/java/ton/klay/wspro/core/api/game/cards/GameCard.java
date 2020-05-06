package ton.klay.wspro.core.api.game.cards;

import ton.klay.wspro.core.api.cards.abilities.components.effects.EffectModification;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.api.cards.abilities.Ability;

public interface GameCard {

    /**
     * Adds an Ability to a card.
     * @param ability A card {@link Ability}
     */
    void registerAbility(Ability ability);

    /**
     * Registers an effect with this card
     * @param property the property that will be altered by the effect (e.g. POWER, SOUL)
     * @param effect the effect that will alter the property of the card.
     */
    void RegisterEffect(String property, EffectModification effect);

    /**
     * get the master of this card. This could change as the game progresses
     * @see <code>Weiss Schwarz rule 4.3</code>
     * @param master the id of the master of this card
     */
    void setMaster(String master);

    /**
     * set the owner of this card. aka the person who brought this card into the game
     * @see <code>Weiss Scharz rule 4.2</code>
     * @param owner the ID of the owner of this card
     */

    void setOwner(String owner);

//    void addTrigger(CardTrigger... cardTriggers);
    //todo confirm whether or not a card have triggers added to it
//    List<CardTrigger> getCardTriggers();

//    Collection<Ability> getAbilities();

    /**
     * get the master of this card. This could change as the game progresses
     * @see <code>Weiss Schwarz rule 4.3</code>
     * @return the id of the master of this card
     */
    String getMaster();

    /**
     * Get the owner of this card. aka the person who brought this card into the game
     * @see <code>Weiss Scharz rule 4.2</code>
     * @return the ID of the owner of this card
     */
    String getOwner();

    /**
     * Get the GUID (Game - Unique - ID) for this particular GameCard.
     * @return a GUID in string form
     */
    String getGUID();

    /**
     * set the position in which this card is currently located at. A card should never be in more than one zone.
     * @param playZone the {@link PlayZone} that should contain.
     */
    void setPosition(PlayZone playZone);

    /**
     * Get the position in which this card is currently located at. A card should never be in more than one zone.
     * @return the {@link PlayZone} that contains this card currently.
     */
    PlayZone getPosition();

    /**
     * Set the orientation of a card. For the standard game, orientation should only come into play if the card is on the stage.
     * @param orientation the orientation in which the card should be placed.
     * @see ton.klay.wspro.core.api.cards.CardOrientation
     */
    void setOrientation(String orientation);

    /**
     * get the orientation of a card. For the standard game, orientation should only come into play if the card is on the stage.
     * @return orientation the orientation in which the card should be placed.
     * @see ton.klay.wspro.core.api.cards.CardOrientation
     */
    String getOrientation();

    /**
     * A card that is being reset should lose all game state knowledge about itself, which could include (but not limited to):
     * <ul>
     *     <li>Losing bonuses that have been granted to it. </li>
     *     <li>Deactivating all event listeners for abilities it created. </li>
     * </ul>
     */
    void reset();

    /**
     * Returns the CollectionID contained within this GameCard
     * @return the collectionID of the card
     */
    String getID();



}

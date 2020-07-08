package ton.klay.wspro.core.api.cards;

import ton.klay.wspro.core.api.game.setup.GameLocale;
import ton.klay.wspro.core.game.cards.CardType;

import java.util.Collection;

public interface PaperCard {

    //Characteristics based on the Rules
    /**
     * Gets a specific card name based on the Locale
     * @see GameLocale
     * @see <code>Weiss Schwarz rule 2.1</code>
     * @return the name of a card in a specific locale.
     */
    Collection<LocalizedString> getCardName();

    int getLevel();

    int getCost();

    /**
     * @see ton.klay.wspro.core.api.cards.CardIcon
     * @return the icon of a card (if it has one)
     */
    CardIcon getIcon();

    /**
     * @see ton.klay.wspro.core.api.cards.CardTrigger
     * @return the trigger of a card (if it has one)
     */
    Collection<CardTrigger> getTriggerIcons();

    int getPower();

    int getSoul();

    Collection<LocalizedString> getTraits();

    /**
     * @see CardColor
     * @return the color associated with this card
     */
    CardColor getColor();

    //additional enumerated characteristics

    /**
     * @see CardType
     * @return the card type associated with this card
     */
    CardType getCardType();

    /**
     * The name of the series which this card appears (to the right of the english name)
     * @see <code>Weiss Schwarz rule 2.13</code>
     * @return the title of the series in which this card appears
     */
    Collection<LocalizedString> getTitleName();

    /**
     * The collection ID of the card. Mostly guaranteed to be unique
     * @see <code>Weiss Schwarz rule 2.14</code>
     * @return the CollectionID of a card
     */
    String getID();


    /**
     * The affiliation of a card, also known in the rules as the Side Frame
     * @see <code>Weiss Schwarz rule 2.18</code>
     * @see CardAffiliation
     * @return the affiliation(s) associated with this particular card.
     */
    CardAffiliation getAffiliations();
}

package ton.klay.wspro.core.api.cards;

import ton.klay.wspro.core.api.game.setup.GameLocale;
import ton.klay.wspro.core.api.cards.CardAffiliation;
import ton.klay.wspro.core.api.cards.CardColor;
import ton.klay.wspro.core.api.scripting.cards.CardType;

import java.awt.*;
import java.util.Collection;

public interface Card {

    //Characteristics based on the Rules
    String getEnCardName();

    String getJpCardName();

    /**
     * Gets a specific card name based on the Locale
     * @see GameLocale
     * @see <code>Weiss Schwarz rule 2.1</code>
     * @return the name of a card in a specific locale.
     */
    String getCardName(String locale);

    int getLevel();

    int getCost();

    /**
     * @see ton.klay.wspro.core.api.cards.CardIcon
     * @return the icon of a card (if it has one)
     */
    String getIcon();

    /**
     * @see ton.klay.wspro.core.api.cards.CardTrigger
     * @return the trigger of a card (if it has one)
     */
    Collection<String> getTriggerIcons();

    String getCardText();

    String getCardText(String locale);

    int getPower();

    int getSoul();

    Collection<String> getTraits();

    /**
     * @see CardColor
     * @return the color associated with this
     */
    String getColor();

    //additional enumerated characteristics



    /**
     * @see CardType
     * @return the card type associated with this card
     */
    String getCardType();

    /**
     * The name of the series which this card appears (to the right of the english name)
     * @see <code>Weiss Schwarz rule 2.13</code>
     * @return the title of the series in which this card appears
     */
    String getTitleName();

    /**
     * The collection ID of the card. Mostly guaranteed to be unique
     * @see <code>Weiss Schwarz rule 2.14</code>
     * @return the CollectionID of a card
     */
    String getID();

    /**
     * The rarity of a card
     * @see <code>Weiss Schwarz rule 2.15</code>
     * @return the rarity associated with a particular card.
     */
    String getRarity();

    /**
     * The affiliation of a card, also known in the rules as the Side Frame
     * @see <code>Weiss Schwarz rule 2.18</code>
     * @see CardAffiliation
     * @return the affiliation(s) associated with this particular card.
     */
    Collection<String> getAffiliations();
}

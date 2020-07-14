package to.klay.wspro.core.game.formats.standard.cards;

import com.google.common.base.MoreObjects;
import to.klay.wspro.core.api.cards.CardAffiliation;
import to.klay.wspro.core.api.cards.CardColor;
import to.klay.wspro.core.api.cards.CardIcon;
import to.klay.wspro.core.api.cards.CardTrigger;
import to.klay.wspro.core.api.cards.LocalizedString;
import to.klay.wspro.core.api.cards.PaperCard;
import to.klay.wspro.core.game.cards.CardType;

import java.util.Collection;
import java.util.Collections;

public class MockCharacterPaperCard implements PaperCard {

    public MockCharacterPaperCard(){};

    @Override
    public Collection<LocalizedString> getCardName() {
        return Collections.singleton(LocalizedString.makeEN("Test Card"));
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public CardIcon getIcon() {
        return null;
    }

    @Override
    public Collection<CardTrigger> getTriggerIcons() {
        return Collections.singleton(CardTrigger.SOUL);
    }

    @Override
    public int getPower() {
        return 3000;
    }

    @Override
    public int getSoul() {
        return 1;
    }

    @Override
    public Collection<LocalizedString> getTraits() {
        return null;
    }

    @Override
    public CardColor getColor() {
        return CardColor.CARD_COLOR_YELLOW;
    }

    @Override
    public CardType getCardType() {
        return CardType.CHARACTER;
    }

    @Override
    public Collection<LocalizedString> getTitleName() {
        return null;
    }

    @Override
    public String getID() {
        return "TestEncoreAbility";
    }

    @Override
    public CardAffiliation getAffiliations() {
        return CardAffiliation.AFFILIATION_WEISS;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .toString();
    }

}
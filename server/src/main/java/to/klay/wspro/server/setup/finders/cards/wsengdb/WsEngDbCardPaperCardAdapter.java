package to.klay.wspro.server.setup.finders.cards.wsengdb;

import com.google.gson.Gson;
import ton.klay.wspro.core.api.cards.CardAffiliation;
import ton.klay.wspro.core.api.cards.CardColor;
import ton.klay.wspro.core.api.cards.CardIcon;
import ton.klay.wspro.core.api.cards.CardTrigger;
import ton.klay.wspro.core.api.cards.LocalizedString;
import ton.klay.wspro.core.api.cards.PaperCard;
import ton.klay.wspro.core.game.cards.CardType;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

class WsEngDbCardPaperCardAdapter implements PaperCard {

    private final WsEngDbCard serializedCard;

    private WsEngDbCardPaperCardAdapter(WsEngDbCard serializedCard){

        this.serializedCard = serializedCard;
    }

    public static WsEngDbCardPaperCardAdapter adapt(WsEngDbCard serializedCard) {
        return new WsEngDbCardPaperCardAdapter(serializedCard);
    }
    public static WsEngDbCardPaperCardAdapter adapt(String jsonCard) {
        WsEngDbCard serializedCard = new Gson().fromJson(jsonCard, WsEngDbCard.class);
        return adapt(serializedCard);
    }


    @Override
    public Collection<LocalizedString> getCardName() {
        return Collections.singleton(LocalizedString.makeEN(serializedCard.getName()));
    }

    @Override
    public int getLevel() {
        return Integer.parseInt(serializedCard.getLevel());
    }

    @Override
    public int getCost() {
        return Integer.parseInt(serializedCard.getCost());
    }

    @Override
    public CardIcon getIcon() {
        for (String abilityText : serializedCard.getAbility()) {
            if (abilityText.toUpperCase().contains("【COUNTER】"))
                return CardIcon.COUNTER;
            if (abilityText.toUpperCase().contains("【CLOCK】"))
                return CardIcon.CLOCK;

        }
        return CardIcon.NONE;
    }

    @Override
    public Collection<CardTrigger> getTriggerIcons() {
        return serializedCard.getTrigger().stream().map(CardTrigger::valueOf).collect(Collectors.toList());
    }

    @Override
    public int getPower() {
        return Integer.parseInt(serializedCard.getPower());
    }

    @Override
    public int getSoul() {
        return serializedCard.getSoul();
    }

    @Override
    public Collection<LocalizedString> getTraits() {
        return serializedCard.getAttributes().stream().map(LocalizedString::makeEN).collect(Collectors.toList());
    }

    @Override
    public CardColor getColor() {
        return CardColor.valueOf("CARD_COLOR_" + serializedCard.getColor());
    }

    @Override
    public CardType getCardType() {
        return CardType.valueOf(serializedCard.getType().toUpperCase());
    }

    @Override
    public Collection<LocalizedString> getTitleName() {
        return Collections.singleton(LocalizedString.makeEN(serializedCard.getExpansion()));
    }

    @Override
    public String getID() {
        return serializedCard.getCode();
    }

    @Override
    public CardAffiliation getAffiliations() {
        String side = serializedCard.getSide();
        if (side.equalsIgnoreCase("S")){
            return CardAffiliation.AFFILIATION_SCHWARZ;
        }
        if (side.equalsIgnoreCase("W")){
            return CardAffiliation.AFFILIATION_WEISS;
        }
        throw new IllegalArgumentException(getID() + " does not have a Side Affiliation");
    }
}

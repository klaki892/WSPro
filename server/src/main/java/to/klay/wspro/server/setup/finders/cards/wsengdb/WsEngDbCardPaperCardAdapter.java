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

package to.klay.wspro.server.setup.finders.cards.wsengdb;

import com.google.gson.Gson;
import to.klay.wspro.core.api.cards.CardAffiliation;
import to.klay.wspro.core.api.cards.CardColor;
import to.klay.wspro.core.api.cards.CardIcon;
import to.klay.wspro.core.api.cards.CardTrigger;
import to.klay.wspro.core.api.cards.LocalizedString;
import to.klay.wspro.core.api.cards.PaperCard;
import to.klay.wspro.core.game.cards.CardType;

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
        try {
            return Integer.parseInt(serializedCard.getLevel());
        } catch (NumberFormatException ex){
            //events and climaxes, dont worry about this
            return 0;
        }
    }

    @Override
    public int getCost() {
        try {
            return Integer.parseInt(serializedCard.getCost());
        } catch (NumberFormatException ex){
            //events and climaxes, dont worry about this
            return 0;
        }

    }

    @Override
    public CardIcon getIcon() {
        for (String abilityText : serializedCard.getAbility()) {
            if (abilityText.toUpperCase().contains("【COUNTER】"))
                return CardIcon.COUNTER;
            if (abilityText.toUpperCase().contains("【CLOCK】"))
                return CardIcon.CLOCK;

        }
        return CardIcon.NO_ICON;
    }

    @Override
    public Collection<CardTrigger> getTriggerIcons() {
        return serializedCard.getTrigger().stream().map(CardTrigger::valueOf).collect(Collectors.toList());
    }

    @Override
    public int getPower() {
        try {
            return Integer.parseInt(serializedCard.getPower());
        } catch (NumberFormatException ex){
            //events and climaxes, dont worry about this
            return 0;
        }
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

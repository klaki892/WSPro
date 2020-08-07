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

package to.klay.wspro.server.grpc.automated;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.CardAffiliation;
import to.klay.wspro.core.api.cards.CardColor;
import to.klay.wspro.core.api.cards.CardIcon;
import to.klay.wspro.core.api.cards.CardTrigger;
import to.klay.wspro.core.api.cards.LocalizedString;
import to.klay.wspro.core.api.cards.PaperCard;
import to.klay.wspro.core.game.Game;
import to.klay.wspro.core.game.cardLogic.ability.TypedAbilityList;
import to.klay.wspro.core.game.cards.CardType;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.server.setup.finders.abilities.QueryableAbilityFinder;
import to.klay.wspro.server.setup.finders.cards.CardFinder;
import to.klay.wspro.server.setup.finders.cards.CardFinderDeckResult;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static to.klay.wspro.server.setup.modules.ServerOptions.GAME_LIMIT_KEY;

public class AutomatedTestingModule extends AbstractModule {

    private static final Logger log = LogManager.getLogger();


    @Provides
    @Named(GAME_LIMIT_KEY)
    @Singleton
    public static int getGameLimit(){
        return 1;
    }

    @Provides
    @Singleton
    public static CardFinder testingCardFinder(){
        return new CardFinder() {
            @Override
            public Optional<PaperCard> getCard(String id) {
                return Optional.of(idOnlyPaperCard(id));
            }

            @Override
            public CardFinderDeckResult sourceDeck(List<String> cardIdList) {
                return CardFinderDeckResult.create(Arrays.asList(idOnlyPaperCard(cardIdList.get(0))),
                        Collections.emptyList());
            }
        };
    }

    @Provides
    @Singleton
    public static QueryableAbilityFinder testingAbilityFinder(){
        return new QueryableAbilityFinder() {
            @Override
            public boolean doesScriptExist(PaperCard card) {
                return false;
            }

            @Override
            public TypedAbilityList getAbilitiesForCard(Game game, PlayingCard card) {
                throw new UnsupportedOperationException("Not Ready for tests");
            }
        };
    }

    static PaperCard idOnlyPaperCard(String id){
        return new PaperCard(){

            @Override
            public Collection<LocalizedString> getCardName() {
                return null;
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
                return null;
            }

            @Override
            public int getPower() {
                return 0;
            }

            @Override
            public int getSoul() {
                return 0;
            }

            @Override
            public Collection<LocalizedString> getTraits() {
                return null;
            }

            @Override
            public CardColor getColor() {
                return null;
            }

            @Override
            public CardType getCardType() {
                return null;
            }

            @Override
            public Collection<LocalizedString> getTitleName() {
                return null;
            }

            @Override
            public String getID() {
                return id;
            }

            @Override
            public CardAffiliation getAffiliations() {
                return null;
            }
        };
    };


}

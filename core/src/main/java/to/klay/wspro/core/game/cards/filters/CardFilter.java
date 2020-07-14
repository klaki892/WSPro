package to.klay.wspro.core.game.cards.filters;

import com.google.common.collect.Sets;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface CardFilter {
    public List<PlayingCard> filter(List<PlayingCard> cards);

    static CardFilter andFilter(CardFilter filter1, CardFilter filter2){
        return cards -> filter1.filter(filter2.filter(cards));

    }

    static CardFilter orFilter (CardFilter filter1, CardFilter filter2){
        return cards -> {
            //use a set to prevent duplicate entries
            HashSet<PlayingCard> filteredSet = new HashSet<>();
            filteredSet.addAll(filter1.filter(cards));
            filteredSet.addAll(filter2.filter(cards));
            return new ArrayList<>(filteredSet);
        };
    }


    static CardFilter andFilter(List<CardFilter> filters){

        return cards -> {
            //use a set to prevent duplicate entries
            Set<PlayingCard> finalSet = new HashSet<>();
            for (CardFilter cardFilter : filters) {
                HashSet<PlayingCard> filteredSet = new HashSet<>(cardFilter.filter(cards));
                finalSet = Sets.intersection(finalSet, filteredSet);
            }
            return new ArrayList<>(finalSet);
        };

    }

    static CardFilter orFilter(List<CardFilter> filters){
        return cards -> {
            //use a set to prevent duplicate entries
            Set<PlayingCard> cardSet = new HashSet<>();
            filters.forEach(cardFilter -> cardSet.addAll(cardFilter.filter(cards)));
            return new ArrayList<>(cardSet);
        };

    };

}

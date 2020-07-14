package to.klay.wspro.core.game.cards.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.LocalizedString;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;

public class TraitFilter extends WholeStringFilter {

    private static final Logger log = LogManager.getLogger();
    private final LocalizedString trait;

    /**
     * Filters a list of cards for a specific trait
     * @param trait the trait to be filtered
     */
    public TraitFilter(LocalizedString trait){
        super(trait);
        this.trait = trait;
    }

    @Override
    boolean condition(PlayingCard card) {
        //run conditional on all traits if any match, pass.
        //todo this could be done better to check for 2xsoul, mixed cases and other things
        return card.getTraits().stream().anyMatch(string -> string.getString().equalsIgnoreCase(trait.getString()));
    }


}

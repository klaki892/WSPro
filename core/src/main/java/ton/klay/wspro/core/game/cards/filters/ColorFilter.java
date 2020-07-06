package ton.klay.wspro.core.game.cards.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.CardColor;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.phases.ClimaxPhase;
import ton.klay.wspro.core.game.formats.standard.phases.MainPhase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ColorFilter extends BaseFilter {

    private static final Logger log = LogManager.getLogger();
    private final CardColor cardColor;


    public ColorFilter(CardColor cardColor){
        this.cardColor = cardColor;
    }
    
    @Override
    boolean condition(PlayingCard card) {
        return card.getColor() == cardColor;
    }

    /**
     * Returns a list of playable colors. Deemed as colors that present in a player's level or clock zone. <br/>
     * intended for  the {@link MainPhase} and the {@link ClimaxPhase}
     * @param clockZone The player's clock zone
     * @param levelZone THe player's level zone
     * @return a card filter which will filter on all colors that appeared in the level or clock.
     */
    public static CardFilter getPlayableColors(PlayZone clockZone, PlayZone levelZone) {
        List<PlayingCard> clockCards = clockZone.getContents();
        List<PlayingCard> levelCards = levelZone.getContents();

        ArrayList<PlayingCard> colorCheckCards = new ArrayList<>(clockCards);
        colorCheckCards.addAll(levelCards);

        List<CardColor> playableColors = colorCheckCards.stream().map(PlayingCard::getColor).collect(Collectors.toList());

        //combine color filters
        List<CardFilter> colorFilterList = playableColors.stream().map(ColorFilter::new).collect(Collectors.toList());

        //level 0 cards dont need color requirement
        return CardFilter.orFilter(colorFilterList);
    }

}

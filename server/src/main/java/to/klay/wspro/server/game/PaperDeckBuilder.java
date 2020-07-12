package to.klay.wspro.server.game;

import ton.klay.wspro.core.api.cards.PaperCard;
import ton.klay.wspro.core.api.game.PaperDeck;

import java.util.List;

public class PaperDeckBuilder implements PaperDeck {
    private String deckName;
    private List<PaperCard> paperCards;

    private PaperDeckBuilder(String deckName, List<PaperCard> paperCards){

        this.deckName = deckName;
        this.paperCards = paperCards;
    }

    public static PaperDeckBuilder createPaperDeck(String deckName, List<PaperCard> paperCards) {
        return new PaperDeckBuilder(deckName, paperCards);
    }

    public PaperDeckBuilder setDeckName(String deckName) {
        this.deckName = deckName;
        return this;
    }

    public List<PaperCard> getCards() {
        return paperCards;
    }

    public PaperDeckBuilder setPaperCards(List<PaperCard> paperCards) {
        this.paperCards = paperCards;
        return this;
    }

    public String getDeckName() {
        return deckName;
    }

}
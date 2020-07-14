package to.klay.wspro.core.game.formats.standard.cards;

import to.klay.wspro.core.api.cards.PaperCard;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.Game;

class MockPlayingCard extends PlayingCard {

    public MockPlayingCard(Game game, PaperCard card, GamePlayer master, GamePlayer owner) {
        super(game, card, master, owner);
    }
}
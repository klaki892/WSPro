package ton.klay.wspro.core.game.formats.standard.cards;

import ton.klay.wspro.core.api.cards.PaperCard;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.Game;

class MockPlayingCard extends PlayingCard {

    public MockPlayingCard(Game game, PaperCard card, GamePlayer master, GamePlayer owner) {
        super(game, card, master, owner);
    }
}
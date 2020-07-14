package to.klay.wspro.core.game.effects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.abilities.components.effects.ContinuousEffect;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.Game;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;

public abstract class SoulChangeContinuousEffect implements ContinuousEffect {

    private static final Logger log = LogManager.getLogger();
    protected final Game game;
    protected final PlayingCard card;
    protected String guid;

    public SoulChangeContinuousEffect(PlayingCard card) {
        this.card = card;
        this.game = card.getGame();
        guid = card.getGame().getRandomGuid();

    }

    @Override
    public boolean isZoneEffect() {
        return false;
    }

    @Override
    public boolean isStateChanging() {
        return true; //changes the amount of soul on a character
    }

    @Override
    public GamePlayer getMaster() {
        return card.getMaster();
    }

    @Override
    public String getGuid() {
        return guid;
    }
}

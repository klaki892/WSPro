package to.klay.wspro.core.api.cards.abilities.components.effects;

import to.klay.wspro.core.api.game.GameEntity;

public interface Effect extends GameEntity {
    void execute(Object... vars);
}

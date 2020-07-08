package ton.klay.wspro.core.api.cards.abilities.components.effects;

import ton.klay.wspro.core.api.cards.abilities.Ability;
import ton.klay.wspro.core.api.game.GameEntity;

public interface Effect extends GameEntity {
    void execute(Object... vars);
    Ability getOwningAbility();
}

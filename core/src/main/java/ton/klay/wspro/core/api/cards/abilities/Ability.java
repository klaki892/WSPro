package ton.klay.wspro.core.api.cards.abilities;

import ton.klay.wspro.core.api.cards.abilities.components.effects.Effect;
import ton.klay.wspro.core.api.game.GameEntity;

public interface Ability extends GameEntity {
    AbilityType getAbilityType();

    void performEffect();
    Effect getEffect();
}

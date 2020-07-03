package ton.klay.wspro.core.api.cards.abilities;

import ton.klay.wspro.core.api.cards.abilities.components.effects.Effect;
import ton.klay.wspro.core.api.game.GameEntity;

import java.util.List;

public interface Ability extends GameEntity {
    AbilityType getAbilityType();

    List<AbilityKeyword> getKeywords();

    void performEffect();
    Effect getEffect();
}

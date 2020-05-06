package ton.klay.wspro.core.api.cards.abilities;

import ton.klay.wspro.core.api.cards.abilities.components.effects.Effect;

public interface Ability {
    AbilityCategory getAbilityType();

    Effect getEffect();
}

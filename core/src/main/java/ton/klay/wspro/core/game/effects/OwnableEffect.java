package ton.klay.wspro.core.game.effects;

import ton.klay.wspro.core.api.cards.abilities.Ability;
import ton.klay.wspro.core.api.cards.abilities.components.effects.Effect;

public interface OwnableEffect extends Effect {


    Ability getOwningAbility();
}

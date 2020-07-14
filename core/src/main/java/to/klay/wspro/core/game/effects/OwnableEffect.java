package to.klay.wspro.core.game.effects;

import to.klay.wspro.core.api.cards.abilities.components.effects.Effect;
import to.klay.wspro.core.game.cardLogic.ability.Ability;

public interface OwnableEffect extends Effect {


    Ability getOwningAbility();
}

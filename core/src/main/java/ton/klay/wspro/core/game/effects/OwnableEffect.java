package ton.klay.wspro.core.game.effects;

import ton.klay.wspro.core.api.cards.abilities.components.effects.Effect;
import ton.klay.wspro.core.game.cardLogic.ability.Ability;

public interface OwnableEffect extends Effect {


    Ability getOwningAbility();
}

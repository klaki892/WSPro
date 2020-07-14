package ton.klay.wspro.core.api.cards.abilities.components.effects;

import ton.klay.wspro.core.game.cardLogic.ability.Ability;
import ton.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;

public interface ReplacementEffect extends Effect {

    BaseTrigger getTrigger();

    boolean isAttackTypeReplacement();
    boolean isOverridingOriginalAction();
    boolean doesAffectCardOrAbility();

    Ability getOwningAbility();
}

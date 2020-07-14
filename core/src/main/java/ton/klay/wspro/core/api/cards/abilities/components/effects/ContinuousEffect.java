package ton.klay.wspro.core.api.cards.abilities.components.effects;

import ton.klay.wspro.core.game.cardLogic.ability.Ability;
import ton.klay.wspro.core.game.formats.standard.FundamentalOrderable;

public interface ContinuousEffect extends Effect, FundamentalOrderable {

    String getGuid();

    boolean meetsCondition();

    boolean shouldUnregister();

    boolean isDependent();
    boolean isZoneEffect();
    boolean isStateChanging();

    Ability getOwningAbility();
}

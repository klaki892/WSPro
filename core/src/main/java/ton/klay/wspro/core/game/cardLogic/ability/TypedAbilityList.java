package ton.klay.wspro.core.game.cardLogic.ability;

import ton.klay.wspro.core.api.cards.abilities.Ability;
import ton.klay.wspro.core.game.formats.standard.triggers.listeners.TriggerableAbilityListener;

import java.util.ArrayList;
import java.util.List;

public class TypedAbilityList {

    private final List<AutomaticAbility> automaticAbilities;
    private final List<ContinuousAbility> continuousAbilities;
    private final List<ActivatedAbility> activatableAbilities;
    private final List<TriggerableAbilityListener> triggerableAbilityListeners;

    public TypedAbilityList(List<Ability> abilities){
        automaticAbilities = new ArrayList<>();
        continuousAbilities = new ArrayList<>();
        activatableAbilities = new ArrayList<>();
        triggerableAbilityListeners = new ArrayList<>();

        for (Ability ability : abilities) {
            if (ability instanceof AutomaticAbility)
                automaticAbilities.add((AutomaticAbility) ability);
            if (ability instanceof ContinuousAbility)
                continuousAbilities.add((ContinuousAbility) ability);
            if (ability instanceof ActivatedAbility)
                activatableAbilities.add((ActivatedAbility) ability);
            if (ability instanceof TriggerableAbilityListener)
                triggerableAbilityListeners.add((TriggerableAbilityListener) ability);
        }

    }

    public List<AutomaticAbility> getAutomaticAbilities() {
        return automaticAbilities;
    }

    public List<ContinuousAbility> getContinuousAbilities() {
        return continuousAbilities;
    }

    public List<ActivatedAbility> getActivateAbilities() {
        return activatableAbilities;
    }

    public List<TriggerableAbilityListener> getTriggerableAbilityListeners() {
        return triggerableAbilityListeners;
    }
}

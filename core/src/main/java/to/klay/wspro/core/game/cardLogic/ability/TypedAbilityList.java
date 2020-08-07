/*******************************************************************************
 *  WSPro - Trading Card Game Simulator
 *  Copyright (C) 2020  Klayton Killough
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published
 *  by the Free Software Foundation, version 3 of the License.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

package to.klay.wspro.core.game.cardLogic.ability;

import to.klay.wspro.core.game.formats.standard.triggers.listeners.TriggerableAbilityListener;

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

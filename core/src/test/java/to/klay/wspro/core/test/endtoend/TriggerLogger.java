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

package to.klay.wspro.core.test.endtoend;

import com.google.common.base.MoreObjects;
import com.google.common.eventbus.Subscribe;
import org.apache.commons.lang3.reflect.FieldUtils;
import to.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;

import java.lang.reflect.Field;

public class TriggerLogger {
    @Subscribe
    public void deadListener(BaseTrigger t){
//                        if (t instanceof PhaseStartedTrigger){
//                            PhaseStartedTrigger t1 = (PhaseStartedTrigger) t;
//                            System.out.println(MoreObjects.toStringHelper(t1)
//                                    .add("name", t.getTriggerName().name())
//                                    .add("Cause", t.getCause())
//                                    .add("caller", t.getCaller().getClass().getSimpleName())
//                                    .add("turnPlayer", t1.getTurnPlayer())
//                                    .toString());
//                            return;
//
//                        }

        Field[] allFields = FieldUtils.getAllFields(t.getClass());
        MoreObjects.ToStringHelper toStringHelper = MoreObjects.toStringHelper(t);
        for (Field field : allFields) {
            if (field.getName().equals("log")) continue;

            try {
                toStringHelper.add(field.getName(),
                        FieldUtils.getField(t.getClass(), field.getName(), true).get(t));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        System.out.println(toStringHelper);
    }

}

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

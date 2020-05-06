package ton.klay.wspro.core.game.formats.standard.abilities.effects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.abilities.components.effects.EffectModification;

import java.util.Comparator;

public class ContinuousEffectComparator<T> implements Comparator<EffectModification<T>> {

    private static final Logger log = LogManager.getLogger();

    @Override
    public int compare(EffectModification<T> o1, EffectModification<T> o2) {
        //todo implement based on the rules for comparing modification types
        return 0;
    }
}

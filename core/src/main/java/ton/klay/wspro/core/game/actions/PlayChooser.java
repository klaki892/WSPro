package ton.klay.wspro.core.game.actions;

import com.google.common.base.MoreObjects;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;

/**
 * Allows the player to make a "Play" by choosing one to many various choices presented
 */
public class PlayChooser {

    private static final Logger log = LogManager.getLogger();
    private final List<PlayChoice> choices;
    private final SelectionType selectionType;
    private final int selectionCount;


    public PlayChooser(List<PlayChoice> choices) {
        this(choices, SelectionType.SINGLE, 1);
    }

    public PlayChooser(List<PlayChoice> choices, SelectionType selectionType, int selectionCount){
        this.choices = Collections.unmodifiableList(choices);
        this.selectionType = selectionType;
        this.selectionCount = selectionCount;

    }

    public List<PlayChoice> getChoices() {
        return choices;
    }

    public int getSelectionCount() {
        return selectionCount;
    }

    public SelectionType getSelectionType() {
        return selectionType;
    }

    /**
     * Holds the type of selections which are possible for a {@link PlayChooser}
     */
    public enum SelectionType {
        SINGLE,
        MULTI,
        UP_TO,
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("selectionType", selectionType)
                .add("selectionCount", selectionCount)
                .add("choices", choices)
                .toString();
    }
}

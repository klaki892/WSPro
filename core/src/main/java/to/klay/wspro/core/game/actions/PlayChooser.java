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

package to.klay.wspro.core.game.actions;

import com.google.common.base.MoreObjects;
import net.badata.protobuf.converter.Converter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.proto.GameMessageProto;
import to.klay.wspro.core.game.proto.PlayRequestProto;
import to.klay.wspro.core.game.proto.ProtoPlayChoice;
import to.klay.wspro.core.game.proto.ProtoSerializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Allows the player to make a "Play" by choosing one to many various choices presented
 */
public class PlayChooser implements ProtoSerializable {

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

    @Override
    public GameMessageProto serializeToProto() {
        PlayRequestProto.Builder builder = PlayRequestProto.newBuilder();

        List<ProtoPlayChoice> protoChoices = new ArrayList<>();
        for (PlayChoice choice : choices) {
            protoChoices.add(Converter.create().toProtobuf(ProtoPlayChoice.class, choice));
        }
        builder.addAllPlayChoices(protoChoices);
        return GameMessageProto.newBuilder().setRequest(builder).build();

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

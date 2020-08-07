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

package to.klay.wspro.core.game.formats.standard.triggers;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.CardOrientation;
import to.klay.wspro.core.api.game.GameEntity;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.proto.CardOrientationTypeConverter;
import to.klay.wspro.core.game.proto.ProtoCardOrientedTrigger;

@ProtoClass(ProtoCardOrientedTrigger.class)
public class CardOrientedTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    @ProtoField
    private final PlayingCard card;
    @ProtoField(converter = CardOrientationTypeConverter.class)
    private final CardOrientation orientedFrom;
    @ProtoField(converter = CardOrientationTypeConverter.class)
    private final CardOrientation orientTo;

    public CardOrientedTrigger(PlayingCard card, CardOrientation orientedFrom, CardOrientation orientTo, TriggerCause cause, GameEntity caller) {
        super(cause, caller);
        this.card = card;
        this.orientedFrom = orientedFrom;
        this.orientTo = orientTo;
    }

    public PlayingCard getCard() {
        return card;
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.CARD_ORIENTED;
    }

    public CardOrientation getOrientedFrom() {
        return orientedFrom;
    }

    public CardOrientation getOrientTo() {
        return orientTo;
    }
}

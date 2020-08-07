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

import com.google.common.base.MoreObjects;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.game.GameEntity;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.proto.ProtoDamageProcessedTrigger;

@ProtoClass(ProtoDamageProcessedTrigger.class)
public class DamageProcessedTrigger extends BaseTrigger {

    private static final Logger log = LogManager.getLogger();
    @ProtoField
    private final PlayingCard sourceCard;
    @ProtoField
    private final int amount;
    @ProtoField
    private final GamePlayer receivingPlayer;
    @ProtoField
    private final boolean damageCancelled;

    public DamageProcessedTrigger(PlayingCard sourceCard, int amount, GamePlayer receivingPlayer,
                                  boolean damageCancelled, TriggerCause cause, GameEntity caller) {
        super(cause, caller);
        this.sourceCard = sourceCard;
        this.amount = amount;
        this.receivingPlayer = receivingPlayer;
        this.damageCancelled = damageCancelled;
    }

    @Override
    public TriggerName getTriggerName() {
        return TriggerName.DAMAGE_PROCESSED;
    }

    public PlayingCard getSourceCard() {
        return sourceCard;
    }

    public int getAmount() {
        return amount;
    }

    public GamePlayer getReceivingPlayer() {
        return receivingPlayer;
    }

    public boolean isDamageCancelled() {
        return damageCancelled;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("sourceCard", sourceCard)
                .add("amount", amount)
                .add("receivingPlayer", receivingPlayer)
                .add("wasCancelled", damageCancelled)
                .toString();
    }
}

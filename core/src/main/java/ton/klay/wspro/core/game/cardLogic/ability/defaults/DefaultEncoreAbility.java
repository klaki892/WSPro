package ton.klay.wspro.core.game.cardLogic.ability.defaults;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.proto.ProtoAbility;
import ton.klay.wspro.core.api.cards.CardOrientation;
import ton.klay.wspro.core.api.cards.Cost;
import ton.klay.wspro.core.api.cards.GameVisibility;
import ton.klay.wspro.core.api.cards.abilities.AbilityKeyword;
import ton.klay.wspro.core.api.cards.abilities.components.effects.Effect;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.cardLogic.ability.AutomaticAbility;
import ton.klay.wspro.core.game.effects.OwnableBaseEffect;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.cards.StockCost;
import ton.klay.wspro.core.game.formats.standard.commands.Commands;
import ton.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.CardEncoredTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.CardMovedTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import ton.klay.wspro.core.game.formats.standard.zones.PlayZone;

import java.util.Collections;
import java.util.List;

@ProtoClass(ProtoAbility.class)
public class DefaultEncoreAbility extends AutomaticAbility {

    private static final Logger log = LogManager.getLogger();

    @ProtoField
    private final GamePlayer master;

    private transient final CardMovedTrigger cardMovedTrigger;
    private transient PlayingCard card;
    protected transient Cost cost;


    public DefaultEncoreAbility(PlayingCard card, CardMovedTrigger trigger) {
        this.card = card;
        master = card.getMaster();
        cardMovedTrigger = trigger;
        cost = new StockCost.Builder().setOwner(card.getOwner()).setCostCount(3).createStockCost();
        effect = new OwnableBaseEffect(this) {
            @Override
            public void execute(Object... vars) {
                if (!cost.isPayable()) return;


                //todo effects/abilties should announce that they are performing?
                //confirm ability usage
                if (!Commands.Utilities.getConfirmationFromPlayer(master)) return;

                CardMovedTrigger varTrigger = (CardMovedTrigger)vars[0];

                PlayZone waitingRoom = master.getPlayArea().getPlayZone(Zones.ZONE_WAITING_ROOM);

                Commands.payCost(cost, this);
                Commands.moveCard(card, waitingRoom, varTrigger.getSourceZone(),
                        Commands.Utilities.getTopOfZoneIndex(varTrigger.getSourceZone()), CardOrientation.REST,
                        GameVisibility.VISIBLE_TO_ALL, TriggerCause.CARD_EFFECT, master);

                BaseTrigger trigger1 = new CardEncoredTrigger(card, TriggerCause.CARD_EFFECT, master);
                master.getGame().getTriggerManager().post(trigger1);
                master.getGame().continuousTiming();
                master.getGame().interruptTiming();
            }
        };
    }

    @Override
    public List<AbilityKeyword> getKeywords() {
        return Collections.singletonList(AbilityKeyword.KEYWORD_ENCORE);
    }

    @Override
    public void performEffect() {
        effect.execute(cardMovedTrigger);
    }

    @Override
    public Effect getEffect() {
        return null;
    }

    @Override
    public GamePlayer getMaster() {
        return card.getMaster();
    }

    @Override
    public Cost getCost() {
        return cost;
    }
}

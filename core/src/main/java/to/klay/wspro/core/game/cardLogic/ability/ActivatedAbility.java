package to.klay.wspro.core.game.cardLogic.ability;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.Cost;
import to.klay.wspro.core.api.cards.abilities.AbilityKeyword;
import to.klay.wspro.core.api.cards.abilities.AbilityType;
import to.klay.wspro.core.api.cards.abilities.components.effects.Effect;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.proto.AbilityKeywordCollectionProtoTypeConverter;
import to.klay.wspro.core.game.proto.AbilityTypeProtoTypeConverter;
import to.klay.wspro.core.game.proto.ProtoAbility;

import java.util.Collection;

/**
 * An AbilityCategory of the category Activation
 * @see <code>Weiss Schwarz Rule 8.1.1.1</code>
 */
@ProtoClass(ProtoAbility.class)
public abstract class ActivatedAbility extends Ability {

    private static final Logger log = LogManager.getLogger();

    @ProtoField(converter = AbilityTypeProtoTypeConverter.class)
    private static final AbilityType category = AbilityType.ABILITY_ACTIVATED;
    @ProtoField(converter = AbilityKeywordCollectionProtoTypeConverter.class)
    protected Collection<AbilityKeyword> keywords;

    private transient Cost cost = null;

    public ActivatedAbility(Cost cost, Effect effect) {
        this.cost = cost;
    }

    public Cost getCost() {
        return cost;
    }

    @Override
    public AbilityType getAbilityType() {
        return category;
    }

    @Override
    public void performEffect() {

    }

    @Override
    public GamePlayer getMaster() {
        return null;
    }
}

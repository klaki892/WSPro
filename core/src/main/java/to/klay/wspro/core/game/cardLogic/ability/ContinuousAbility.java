package to.klay.wspro.core.game.cardLogic.ability;

import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.abilities.AbilityKeyword;
import to.klay.wspro.core.api.cards.abilities.AbilityType;
import to.klay.wspro.core.api.cards.abilities.components.effects.Effect;
import to.klay.wspro.core.game.formats.standard.FundamentalOrderable;
import to.klay.wspro.core.game.proto.AbilityKeywordCollectionProtoTypeConverter;
import to.klay.wspro.core.game.proto.AbilityTypeProtoTypeConverter;
import to.klay.wspro.core.game.proto.ProtoAbility;

import java.util.Collection;

/**
 * An AbilityCategory of the category Continious
 *
 * @see <code>Weiss Schwarz Rule 8.1.1.3</code>
 */
@ProtoClass(ProtoAbility.class)
public abstract class ContinuousAbility extends Ability implements FundamentalOrderable {

    private static final Logger log = LogManager.getLogger();
    @ProtoField(converter = AbilityTypeProtoTypeConverter.class)
    private static final AbilityType category = AbilityType.ABILITY_CONTINUOUS;

    @ProtoField(converter = AbilityKeywordCollectionProtoTypeConverter.class)
    protected Collection<AbilityKeyword> keywords;

    public ContinuousAbility(Effect effect) {
    }


    @Override
    public AbilityType getAbilityType() {
        return category;
    }

    @Override
    public abstract void performEffect();


}

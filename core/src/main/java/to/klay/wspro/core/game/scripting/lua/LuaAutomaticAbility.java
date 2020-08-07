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

package to.klay.wspro.core.game.scripting.lua;

import com.google.common.eventbus.EventBus;
import net.badata.protobuf.converter.annotation.ProtoClass;
import net.badata.protobuf.converter.annotation.ProtoField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import to.klay.wspro.core.api.cards.Cost;
import to.klay.wspro.core.api.cards.abilities.AbilityKeyword;
import to.klay.wspro.core.api.cards.abilities.components.effects.Effect;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.cardLogic.ability.AutomaticAbility;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.listeners.TriggerableAbilityListener;
import to.klay.wspro.core.game.proto.AbilityKeywordCollectionProtoTypeConverter;
import to.klay.wspro.core.game.proto.ProtoAbility;

import java.util.Arrays;
import java.util.List;

@ProtoClass(ProtoAbility.class)
public class LuaAutomaticAbility extends AutomaticAbility implements TriggerableAbilityListener  {

    private static final Logger log = LogManager.getLogger();
    @ProtoField
    private final PlayingCard card;
    @ProtoField(converter = AbilityKeywordCollectionProtoTypeConverter.class)
    private final List<AbilityKeyword> keywords;

    private transient Effect effect;
    private final transient  LuaFunction triggerCondition;
    private final transient  Cost cost;
    private transient EventBus eventBus;

    public LuaAutomaticAbility(PlayingCard card, List<AbilityKeyword> keywords,
                               Effect effect,
                               LuaFunction triggerCondition,
                               Cost cost
                               ){
        this.card = card;
        this.keywords = keywords;
        this.effect = effect;
        this.triggerCondition = triggerCondition;
        this.cost = cost;
    }

    @Override
    public List<AbilityKeyword> getKeywords() {
        return keywords;
    }

    @Override
    public void performEffect() {
        effect.execute();
    }

    @Override
    public Effect getEffect() {
        return effect;
    }

    public void setEffect(LuaEffect.Builder effect) {
        this.effect = effect.setOwner(this).createLuaEffect();
    }

    @Override
    public GamePlayer getMaster() {
        return card.getMaster();
    }

    @Override
    public void triggerReceived(BaseTrigger trigger) {
        triggerCondition.call(CoerceJavaToLua.coerce(trigger));
    }

    @Override
    public void register(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.register(this);
    }

    @Override
    public void deregister() {
        eventBus.unregister(this);
    }

    @Override
    public Cost getCost() {
        return cost;
    }

    public static class Builder {
        private List<AbilityKeyword> keywords;
        private LuaEffect luaEffect;
        private LuaFunction triggerCondition;
        private LuaCost luaCost;
        private PlayingCard card;


        public Builder setCard(PlayingCard card) {
            this.card = card;
            return this;
        }

        public Builder setKeywords(AbilityKeyword keyword){
            return setKeywords(new AbilityKeyword[]{keyword});
        }
        public Builder setKeywords(AbilityKeyword... keywords) {
            this.keywords = Arrays.asList(keywords);
            return this;
        }

        public Builder setEffect(LuaEffect.Builder effect) {
            this.luaEffect = effect.createLuaEffect();
            return this;
        }

        public Builder setTriggerCondition(LuaFunction triggerCondition) {
            this.triggerCondition = triggerCondition;
            return this;
        }

        public Builder setCost(LuaCost.Builder cost) {
            this.luaCost = cost.createLuaCost();
            return this;
        }

        public LuaAutomaticAbility createLuaAutomaticAbility() {
            //Cost needs to have its owner set
            LuaAutomaticAbility ability = new LuaAutomaticAbility(card, keywords, luaEffect, triggerCondition, luaCost);
            luaCost.setCostOwner(ability);
            return ability;
        }
    }
}

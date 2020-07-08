package ton.klay.wspro.core.game.scripting.lua;

import com.google.common.eventbus.EventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import ton.klay.wspro.core.api.cards.Cost;
import ton.klay.wspro.core.api.cards.abilities.AbilityKeyword;
import ton.klay.wspro.core.api.cards.abilities.components.effects.Effect;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.cardLogic.ability.AutomaticAbility;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.listeners.TriggerableAbilityListener;

import java.util.Arrays;
import java.util.List;

public class LuaAutomaticAbility extends AutomaticAbility implements TriggerableAbilityListener  {

    private static final Logger log = LogManager.getLogger();
    private final PlayingCard card;
    private final List<AbilityKeyword> keywords;
    private Effect effect;
    private final LuaFunction triggerCondition;
    private final Cost cost;
    private EventBus eventBus;

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

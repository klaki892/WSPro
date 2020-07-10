package ton.klay.wspro.core.game.scripting.lua;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;
import ton.klay.wspro.core.api.cards.PaperCard;
import ton.klay.wspro.core.api.cards.abilities.Ability;
import ton.klay.wspro.core.game.AbilityFinder;
import ton.klay.wspro.core.game.Game;
import ton.klay.wspro.core.game.cardLogic.ability.TypedAbilityList;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

public abstract class LuaAbilityFinder  implements AbilityFinder {

    private static final Logger log = LogManager.getLogger();
    private Duration totalTime = Duration.ZERO;
    private HashMap<String, String> idToScriptMap = new HashMap<>(100);
    public abstract Optional<String> getLuaScript(PaperCard card);

    @Override
    public TypedAbilityList getAbilitiesForCard(Game game, PlayingCard card) {
        Instant start = Instant.now();
        Optional<String> potentialLuaScript;
        if (idToScriptMap.containsKey(card.getID())) {
            potentialLuaScript = Optional.of(idToScriptMap.get(card.getID()));
        } else {
            potentialLuaScript = getLuaScript(card.getPaperCard());
        }
        Instant end = Instant.now();
        totalTime = totalTime.plus(Duration.between(start, end));
        log.debug("Total time retrieving card scripts so far:" + totalTime.toMillis());
        log.warn("No Scripts found for " + card + "It will be initialized without abilities");
        if (!potentialLuaScript.isPresent()) return new TypedAbilityList(Collections.emptyList());

        String scriptContent = potentialLuaScript.get();
        idToScriptMap.putIfAbsent(card.getID(), scriptContent);

        Globals engine = LuaScriptEngine.initLuaJEngine(game);

        //call the script so methods are loaded into memory
        engine.load(scriptContent).call();



        //get abilities
        try {

            LuaFunction getAbilities = engine.get("getAbilities").checkfunction();
            LuaValue[] args = {CoerceJavaToLua.coerce(card)};
            Varargs luaAbilityBuilderList = getAbilities.invoke(args);
            ArrayList<Ability> abilities = new ArrayList<>();

            //coerece the builders back into abilities

            for (int i = 1; i <= luaAbilityBuilderList.narg(); i++) {
                LuaValue abilityBuilder = luaAbilityBuilderList.arg(i);
                Optional<Ability> ability = convertLuaAbility(abilityBuilder);
                if (ability.isPresent()){
                    abilities.add(ability.get());
                } else {
                    log.error("Unable to get ability " + i + " from script");
                }
            }

            return new TypedAbilityList(abilities);

        } catch (LuaError ex){
            log.error(String.format("Failed to load abilities for %s from script", card.getID()), ex);
        }

        return new TypedAbilityList(Collections.emptyList());
    }

    private Optional<Ability> convertLuaAbility(LuaValue luaAbilityBuilder) {

        //try automatic ability
        try {
            LuaAutomaticAbility.Builder abilityBuilder = (LuaAutomaticAbility.Builder) CoerceLuaToJava.coerce
                    (luaAbilityBuilder, LuaAutomaticAbility.Builder.class);
            return Optional.of(abilityBuilder.createLuaAutomaticAbility());
        } catch (ClassCastException ex){
            //this is expected to happen 1/3 of the time
            log.trace(ex);
        }
        //todo try continuous ability
        //todo try activated ability

        return Optional.empty();
    }
}

package to.klay.wspro.core.game.scripting.lua.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.scripting.lua.LuaAutomaticAbility;

public class AbilityFactory {

    private static final Logger log = LogManager.getLogger();

    public static LuaAutomaticAbility.Builder createAutomatic(PlayingCard card){
        return new LuaAutomaticAbility.Builder().setCard(card);
    }
    public static LuaAutomaticAbility standbyAutomaticAbility(LuaAutomaticAbility.Builder builder){
        LuaAutomaticAbility automaticAbility = builder.createLuaAutomaticAbility();
        automaticAbility.getMaster().getGame().getTimingManager().add(automaticAbility);
        return automaticAbility;
    }
}

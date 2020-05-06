package randomtests;

import ton.klay.weebsports.coregame.gameplay.cardLogic.ability.component.Conditions;
import ton.klay.weebsports.coregame.gameplay.cards.Card;
import ton.klay.weebsports.coregame.gameplay.cardLogic.effects.Effect;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.File;
import java.net.URISyntaxException;

public class testmain {

    public static void main(String[] args) throws IllegalAccessException, URISyntaxException {
//        System.out.println("Hello world");

        File file = new File(testmain.class.getResource("/lua/testLuaFile.lua").toURI());

        Globals globals = JsePlatform.standardGlobals();
//        Reader reader = new InputStreamReader(randomtests.testmain.class.getResourceAsStream(file));
//        LuaValue luaValue = globals.load(reader, "test");
        globals.loadfile(file.getAbsolutePath());

        Card testFunction = new Card();
        testFunction.setCardName("lol");
        LuaValue luaVals = CoerceJavaToLua.coerce(testFunction);
        LuaValue effectCoerce = CoerceJavaToLua.coerce(new Effect());

        globals.set("Effect", effectCoerce);
//        for (Field field : ton.klay.weebsports.coregame.gameplay.cardLogic.effects.Effect.class.getDeclaredFields())
//            if (Modifier.isStatic(field.getModifiers()))
//                globals.set(field.getName(), CoerceJavaToLua.coerce(field.get(null)));
        for (Conditions timing : Conditions.values())
                System.out.println(timing);
//            globals.set(timing.name(), CoerceJavaToLua.coerce(timing));

//        globals.set("ON_PLAY", CoerceJavaToLua.coerce(ton.klay.weebsports.coregame.gameplay.cardLogic.effects.Effect.ON_PLAY));
//        if (luaValue.isfunction())
//            luaValue.call(luaVals);
//        else
//            System.out.println("somethingBroke");

        LuaValue luaTestFunction = globals.get("InitalizeCard");
        if (!luaTestFunction.isnil())
            luaTestFunction.call(luaVals);
        else
            System.out.println("somethingBroke");

        System.out.println(testFunction.getCardName());
        System.out.println(testFunction.getEffect());



//        LuaFunction luaFunction = luaValue.checkfunction();
//        System.out.println(luaFunction.narg());

//        LuaTable luaTable = luaValue.

//        LuaValue e1
//        LuaFunction e2 = luaValue.get("e2").checkfunction();



//        OneArgFunction oneArgFunction = new OneArgFunction() {
//            @Override
//            public LuaValue call(LuaValue luaValue) {
//                return luaValue.get("e2").checkfunction().arg1();
//            }
//        };

//        LuaString luaString = e2.arg1().checkstring();
//        System.out.println(luaString.tojstring());

//        chunck.call();
    }
}


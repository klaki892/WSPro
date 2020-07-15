package to.klay.wspro.core.proto;

import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableSet;
import com.google.common.eventbus.Subscribe;
import com.google.common.reflect.ClassPath;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import net.webby.protostuff.runtime.Generators;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * This extracts all {@link BaseTrigger} derived classes and runs them through a protobuf generator, also generating
 * definitions for all classes that are included within the triggers (ex: {@link PlayingCard})
 * <br/>
 * This process cant be automated (chicken&egg paradox with `core` package) and should only be used when major
 * changes have happened in the structure of core, or new objects need to be serialized.
 */
public class CoreProtoGenerator {

    private static final Logger log = LogManager.getLogger();
    static HashSet<String> protoDefs = new HashSet<>();
    static List<String> triggerNames = new ArrayList<>();
    static HashMap<String, String> renameIndex = new HashMap<>();

    public static void main(String[] args) throws IOException {
//        Player player1 = new TestPlayer("TestPlayer1", new MockStandardDeck(), TestPlayerControllers::commandLinePlayChoiceMaker);
//        Player player2 = new TestPlayer("TestPlayer2", new MockStandardDeck(), TestPlayerControllers::commandLinePlayChoiceMaker);
//
//        Game game = new Game(player1, player2, new TestLocalStorageLuaAbilityFinder());
//
//        game.getTriggerManager().register(new TriggerLogger());
//        game.getTriggerManager().register(new protoPrintListener());
//
//
//
//        game.startGame();

        ClassPath classPath = ClassPath.from(ClassLoader.getSystemClassLoader());

        ImmutableSet<ClassPath.ClassInfo> topLevelClasses = classPath.getTopLevelClasses("to.klay.wspro.core.game.formats.standard.triggers");

        for (ClassPath.ClassInfo triggerClassInfo : topLevelClasses) {
            Class<?> triggerClass = triggerClassInfo.load();
            if (Modifier.isAbstract(triggerClass.getModifiers())) continue;
            Schema<? extends BaseTrigger> schema = (Schema<? extends BaseTrigger>) RuntimeSchema.getSchema(triggerClass);
            String content = Generators.newProtoGenerator(schema).generate();
            filterProtoFile(content);
        }

        //insert header
        String header = "/**\n" +
                "AUTOGENERATED FROM CORE-PROTO. DO NOT EDIT.\n" +
                " */\n" +
                "syntax = \"proto3\";\n" +
                "\n" +
                "option java_multiple_files = true;\n" +
                "option java_package = \"to.klay.wspro.core.game.proto\";\n" +
                "option java_outer_classname = \"ProtoEntities\";\n\n";
//                + "package to.klay.wspro.core.game.proto;\n\n";

        List<String> defs = new ArrayList<>(protoDefs);


        //remove duplicates messages - Enums were getting Messages with "ordinal" and "name"
        //identify the "Triggers" for GamTrigger Proto OneOf
        for (int i = defs.size() - 1; i >= 0; i--) {
            String item = defs.get(i);
            if (item.contains("ordinal")) {
                defs.remove(item);
                continue;
            }
            String messageDeclaration = item.split("\n")[0];
            String type = messageDeclaration.split(" ")[0];
            String name = messageDeclaration.split(" ")[1];

            //Prepend Proto in front of every message/enum type name
            renameIndex.put(name, "Proto"+name);


            //pull trigger names for the GameTriggerProto Generation
            if (type.trim().equalsIgnoreCase("message") &&
                    name.trim().contains("Trigger")){
                triggerNames.add(name);
            }

        }

        //generate GameTriggerProto
        String gameTriggerProtodef =
                "message GameTriggerProto {\n" +
                        "  oneof triggerType {\n" +
                        "  GENERATED\n" +
                        "  }\n" +
                        "}\n";
        StringBuilder oneOfTypes = new StringBuilder();

        //sort to prevent less randomization changes
        triggerNames.sort(Comparator.naturalOrder());
        for (int i = 0; i < triggerNames.size(); i++) {
            //to camelcase
            String formattedName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, triggerNames.get(i));
            oneOfTypes.append("    "+ triggerNames.get(i) + " " + formattedName + " = " + (i+1) + ";\n");
        }

        gameTriggerProtodef = gameTriggerProtodef.replaceAll("  GENERATED", oneOfTypes.toString());
        defs.add(gameTriggerProtodef);

        StringBuilder sb = new StringBuilder(header);
        //sort to prevent less randomization changes
        defs.sort(Comparator.naturalOrder());
        defs.forEach(str -> sb.append(str+"\n\n"));

        //rename all fields with their Prefix

        final String[] outputString = {sb.toString()};
        renameIndex.forEach((s, s2) -> {
            outputString[0] = outputString[0].replaceAll(s, s2);
        });


        //generate playchoices protobuf

        FileWriter fw = new FileWriter("ProtoEntities.proto");
        fw.write(outputString[0]);
        fw.flush();
        fw.close();
    }

    private static String filterProtoFile(String content) {
        String finalProto = content;
//        filter header
        String headerRegex = "package .*\\n\\nimport .*\\n\\noption .*\\n\\n";
        finalProto = content.replaceAll("package .*\\n\\nimport .*\\n\\noption .*\\n\\n", "");
        //for proto3 spec
        finalProto = finalProto.replaceAll("optional", "");

        String[] noSpacingDefinitions = finalProto.split("\n\n");
        protoDefs.addAll(Arrays.asList(noSpacingDefinitions));

        //filter out all newLines
//        finalProto = finalProto.replaceAll("\\n", "");

        return finalProto;
    }

    public static class protoPrintListener {

        @Subscribe
        void handleEvent(BaseTrigger t){
            Schema<? extends BaseTrigger> schema = RuntimeSchema.getSchema(t.getClass());
            String content = Generators.newProtoGenerator(schema).generate();
            System.out.println(content);
        }
    }

}
package to.klay.wspro.core.game.formats.standard.triggers;

import com.google.common.base.MoreObjects;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Message;
import net.badata.protobuf.converter.Converter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.game.GameEntity;
import to.klay.wspro.core.api.game.GameRuntimeException;
import to.klay.wspro.core.game.proto.GameMessageProto;
import to.klay.wspro.core.game.proto.GameTriggerProto;
import to.klay.wspro.core.game.proto.ProtoSerializable;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseTrigger implements ProtoSerializable  {


    public static transient java.time.Duration totalTimeSerializing = Duration.ZERO;
    public static transient java.time.Duration totalTimeClassPathRetrieving = Duration.ZERO;
    public static final transient Map<Class<? extends BaseTrigger>, FieldDescriptProtoClassPair> serializationMappings = new HashMap<>();
    public static transient ClassPath classPath = null;

    private static final Logger log = LogManager.getLogger();
    private final transient TriggerCause cause;
    private final transient GameEntity caller;

    public BaseTrigger(TriggerCause cause, GameEntity caller) {
        this.cause = cause;
        this.caller = caller;
    }

    public abstract TriggerName getTriggerName();

    public TriggerCause getCause() {
        return cause;
    }

    public GameEntity getCaller() {
        return caller;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("Name", getTriggerName())
                .add("cause", cause)
                .add("caller", caller)
                .toString();
    }

    /**
     * This method Reflectively looks through the classpath for the corresponding Protobuf Trigger to the one being used by the game.<br/>
     * It needs to lookup the Protobuf class definition and the specific {@link FieldDescriptor} inside {@link GameTriggerProto}
     * This is done simply to save duplicated code work that would exist inside every trigger.
     * For the lack of coding, we do take a performance hit:
     * <ul>
     *  <li>a game that can run in 2 seconds will 1+ min serialization overhead without caching</li>
     *  <li>a game that can run in 2 seconds will take an additional ~2 seconds with caching for the first game executed</li>
     *  <li>all future games after a built (static) cache will run with ~100 millisecond overhead </li>
     * </ul>
     * <b>Note (why static cache & classPath enumeration) - this is likely the only static variables in all of Core due to mappings not changing between games.</b>
     * @return The Protobuf serialized form of this Trigger
     */
    @Override
    public GameMessageProto serializeToProto() {

        //check cache for Proto-Normal mappings
        Stopwatch stopwatch = Stopwatch.createStarted();
        if (serializationMappings.containsKey(this.getClass())){
            FieldDescriptProtoClassPair pair = serializationMappings.get(this.getClass());

            GameMessageProto gameMessageProto = makeProtoGameMessage(pair.protoClass, pair.descriptor,this);
            stopwatch.stop();

            totalTimeSerializing = totalTimeSerializing.plus(stopwatch.elapsed());
            log.trace(String.format("Serialzation total Time: %s", totalTimeSerializing.toMillis()));
            return gameMessageProto;
        }

        //obtain classpath lookup defintiions for all classes
        try {
            classPath = classPath == null ? ClassPath.from(ClassLoader.getSystemClassLoader()) : classPath;
            totalTimeClassPathRetrieving = totalTimeClassPathRetrieving.plus(stopwatch.elapsed());
            log.trace(String.format("ClassPath Retrieving  total Time: %s", totalTimeClassPathRetrieving.toMillis()));

        } catch (IOException e) {
            throw new GameRuntimeException("Failed to Parse class to Proto", e);
        }
        ImmutableSet<ClassPath.ClassInfo> topLevelClasses = classPath.getTopLevelClasses("to.klay.wspro.core.game.proto");
        String thisClassName = this.getClass().getSimpleName();

        //find the filed in GameTriggerProto corresponding to our trigger, and make and return the GameMessageProto
        for (ClassPath.ClassInfo protoClassInfo : topLevelClasses) {
            if (protoClassInfo.getSimpleName().equalsIgnoreCase("Proto"+ thisClassName)){
                //noinspection unchecked - We can reasonably assume from ProtoEntities.proto that a class named Proto+thistrigger is a protobuf class
                Class<? extends Message> protoClass = (Class<? extends Message>) protoClassInfo.load();
                for (FieldDescriptor fieldDescriptor : GameTriggerProto.getDescriptor().getFields()) {
                    if (fieldDescriptor.getName().toLowerCase().contains(thisClassName.toLowerCase())){
                        serializationMappings.put(this.getClass(), new FieldDescriptProtoClassPair(fieldDescriptor, protoClass));
                        GameMessageProto gameMessageProto = makeProtoGameMessage(protoClass, fieldDescriptor, this);
                        stopwatch.stop();
                        totalTimeSerializing = totalTimeSerializing.plus(stopwatch.elapsed());
                        log.trace(String.format("Serialzation total Time: %s", totalTimeSerializing.toMillis()));
                        return gameMessageProto;
                    }
                }

            }
        }
        //if this gets thrown the entire game is broken because we can communicate an event.
        throw new GameRuntimeException("Failed to ProtoParse a Trigger," +
                " couldnt find proto class ("+ thisClassName +")");
    }

    private static GameMessageProto makeProtoGameMessage(Class<? extends Message> protoClass, FieldDescriptor descriptor, BaseTrigger normalTrigger) {
        Message protoObject = Converter.create().toProtobuf(protoClass, normalTrigger);
        GameTriggerProto gameTriggerProto = GameTriggerProto.newBuilder().setField(descriptor, protoObject).build();
        return GameMessageProto.newBuilder().setTrigger(gameTriggerProto).build();
    }

    private static class FieldDescriptProtoClassPair{
        FieldDescriptor descriptor;
        Class<? extends Message> protoClass;

        FieldDescriptProtoClassPair(FieldDescriptor descriptor, Class<? extends Message> protoClass){
            this.descriptor = descriptor;
            this.protoClass = protoClass;
        }
    }
}

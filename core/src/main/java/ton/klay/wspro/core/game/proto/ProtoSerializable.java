package ton.klay.wspro.core.game.proto;

import to.klay.wspro.core.game.proto.GameMessageProto;

public interface ProtoSerializable {

    GameMessageProto serializeToProto();
}

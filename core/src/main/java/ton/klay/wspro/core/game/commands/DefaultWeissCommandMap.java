package ton.klay.wspro.core.game.commands;

import ton.klay.wspro.core.api.game.commands.CommandExecution;
import ton.klay.wspro.core.game.commands.highlevel.PlayerResponseCommand;
import ton.klay.wspro.core.game.commands.highlevel.PlaytimingCommand;
import ton.klay.wspro.core.game.commands.lowlevel.*;
import ton.klay.wspro.core.game.commands.midlevel.MoveCardCommand;
import ton.klay.wspro.core.game.commands.midlevel.ShuffleCommand;
import ton.klay.wspro.core.game.commands.highlevel.DrawCardCommand;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class DefaultWeissCommandMap {
    public static Map<String, Class<? extends CommandExecution>> obtain() {
        HashMap<String, Class<? extends  CommandExecution>> commandMapping = new HashMap<>();

        ////////////////////////MAP OF ALL COMPILED COMMANDS///////////////////
        commandMapping.put(GameStartCommand.CMD_NAME, GameStartCommand.class);
        commandMapping.put(GameEndCommand.CMD_NAME, GameEndCommand.class);
        commandMapping.put(GameErrorCommand.CMD_NAME, GameErrorCommand.class);
        commandMapping.put(NextPhaseCommand.CMD_NAME, NextPhaseCommand.class);
        commandMapping.put(AddCardCommand.CMD_NAME, AddCardCommand.class);
        commandMapping.put(ShuffleCommand.CMD_NAME, ShuffleCommand.class);
        commandMapping.put(MoveCardCommand.CMD_NAME, MoveCardCommand.class);
        commandMapping.put(DrawCardCommand.CMD_NAME, DrawCardCommand.class);
        commandMapping.put(PlayerResponseCommand.CMD_NAME, PlayerResponseCommand.class);
        commandMapping.put(PlaytimingCommand.CMD_NAME, PlaytimingCommand.class);

        return Collections.unmodifiableMap(commandMapping);
    }
}

package ton.klay.wspro.core.game.communication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.LoseConditions;
import ton.klay.wspro.core.api.game.commands.Command;
import ton.klay.wspro.core.api.game.commands.CommandSender;
import ton.klay.wspro.core.api.game.commands.CommandSenderType;
import ton.klay.wspro.core.api.game.communication.Communicable;
import ton.klay.wspro.core.api.game.communication.Communicator;
import ton.klay.wspro.core.api.game.communication.GameCommunicationManager;
import ton.klay.wspro.core.api.game.communication.rules.CommunicationRuleBook;
import ton.klay.wspro.core.api.game.events.Event;
import ton.klay.wspro.core.api.game.events.EventListener;
import ton.klay.wspro.core.api.game.setup.GameContext;
import ton.klay.wspro.core.game.events.gameissued.GameEndEvent;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DefaultGameCommunicationManager implements GameCommunicationManager, EventListener {

    private static final Logger log = LogManager.getLogger();
    private final Map<String, CommChannel> communicationMap;
    private final ExecutorService messageDispatcher;
    private final String gameID;
    private final CommunicationRuleBook communicationRuleBook;
    private GameContext context;

    public DefaultGameCommunicationManager(String GameID, GameContext context){
        gameID = GameID;
        this.context = context;
        //register ourself with the context event listener
        context.getEventManager().addEventListener(this);
        communicationMap = new HashMap<>();

        //todo get a commnucaiton rule book (or make one ourselves)
        communicationRuleBook = null;


        messageDispatcher = Executors.newSingleThreadExecutor();

    }

    @Override
    public void sendMessage(Communicable message, String communicatorID) {
        CommChannel commChannel;
        if ((commChannel = communicationMap.get(communicatorID)) != null){
            //todo check the rulebook
            messageDispatcher.execute(() -> dispatchMessage(message, commChannel));
        } else {
            log.error("Attempted to send message (" + message.toCommunicableString() + ") to ID: " + communicatorID + ". But ID was not found.");
        }

    }

    /**
     * Task for Dispatching the message to the communicator. Executes Asynchronously.
     * @param message the message being sent
     * @param commChannel the communicator receiving the message
     */
    private void dispatchMessage(Communicable message, CommChannel commChannel){
        try {
            log.trace("Dispatching message (" + message.toCommunicableString() + ") to ID: " + commChannel.getID() + ".");
            commChannel.sendMessage(message);
        } catch (RuntimeException rex){
            log.error("Exception occurred in message dispatch to communicator: " + commChannel.getID());

            //if the message error happens to any entity that needs information (aka not observers) end the game.
            //we dont know how important that lost message just was.
            EnumSet<CommandSenderType> fatalSenders = EnumSet.of(CommandSenderType.PLAYER, CommandSenderType.GAME, CommandSenderType.CARD);
            if (fatalSenders.contains(commChannel.getSender().getSenderType())) {
                log.fatal("Ending Game from message that failed to dispatch to important entity.", rex);
                context.endGame(LoseConditions.GAME_ERROR, Collections.emptyList());
            }

        }
    }

    @Override
    public void messageAll(Communicable message) {
        log.debug("Dispatching message (" + message.toCommunicableString() + ") to All registered communicators.");
        for (CommChannel commChannel: communicationMap.values()) {
            //todo check the rulebook
            messageDispatcher.execute(() -> dispatchMessage(message, commChannel));
        }
    }

    @Override
    public void decode(Communicable message, CommandSender sender) {
        //todo manage the message (which is likely a command)

        //pass the message into a command parser
        Command command = context.getCommandDecoder().decode(message, sender);
        if (command != null) {
            log.debug("Received a command (" + command.getKeyword() + "), passing it to the executor");

            //with the ICommand, pass it to the command handler
            context.getCommandExecutor().issueCommand(command);
        } else {
            log.error("Unable to decode message: " + message.toCommunicableString());
        }
    }

    @Override
    public void encode(Event event) {
        //todo when we receive an event, look at it and see who/how many should be recieving it
        //todo invalidCommand goes back to that user only.
        //todo playtimingEvent should strip most of its content for all except the player.

        messageAll(event);//FIXME REMOVE ME. TEMP
    }

    @Override
    public void setContext(GameContext context) {
        this.context = context;
    }

    @Override
    public void addCommunicator(Communicator communicator, CommandSenderType senderType) {
        log.info("Added new Communicator: " + communicator.getID());
        String id;
        if ((id = communicator.getID()) != null && !communicator.getID().isEmpty()) {
            CommChannel commChannel = new CommChannel(communicator, this, senderType);
            CommChannel prevComm = communicationMap.put(id, commChannel);
            if (prevComm != null)
                log.warn("Removed previous communicator associated with ID: " + id);
        }
    }

    @Override
    public void removeCommunicator(Communicator communicator) {
        CommChannel channel;

        if ((channel = communicationMap.remove(communicator.getID())) != null) {
            channel.close();
            log.info("Removed communicator associated with ID: " + communicator.getID());
        } else {
            log.error("Attempted to remove communicator: " + communicator.getID() + " but it wasn't present.");
        }

    }

    public String getID() {
        return gameID;
    }

    /**
     * Receivers a mesasge from a particular sender
     * @param message message received
     * @param sender the sender of the message we just recieved
     */
    public void receiveMessage(Communicable message, CommandSender sender) {
        decode(message, sender);
    }

    @Override
    public void update(Event event) {
        log.debug("Communication Manager received event : " + event.getName());
        encode(event);

        //if its a game shutdown event, begin shutdown
        if (event instanceof GameEndEvent) {
            messageDispatcher.shutdown();
            log.info("Shutting down Communication Manager...");
            communicationMap.values().forEach(CommChannel::close);
            log.info("Communication Manager closed all connections.");
        }
    }


    class CommChannel {
        private Communicator incoming, outgoing;
        private final  DefaultGameCommunicationManager manager;
        private final CommandSender sender;

        CommChannel(Communicator communicator, DefaultGameCommunicationManager manager, CommandSenderType senderType){
            this.incoming = communicator;
            this.manager = manager;

            //create the sender
            sender = new CommandSender() {
                @Override
                public String getName() {
                    return communicator.getID();
                }

                @Override
                public CommandSenderType getSenderType() {
                    return senderType;
                }
            };

            //create our internal communicator which is what the other side listens to
            outgoing = new Communicator() {

                boolean closed = false;

                @Override
                public void sendMessage(Communicable message) {
                    incoming.receiveMessage(message);
                }

                @Override
                public void addMessageReceiver(Communicator communicator) {
                    //externally handled
                }

                @Override
                public void removeMessageReceiver(Communicator communicator) {
                    //externally handled
                    closed = true;
                }

                @Override
                public String getID() {
                    return manager.getID();
                }

                @Override
                public void receiveMessage(Communicable message) {
                    if (!closed) {
                        manager.receiveMessage(message, sender);
                    }
                }
            };

            register();
        }

        private void register() {

            outgoing.addMessageReceiver(incoming);
            incoming.addMessageReceiver(outgoing);
        }

        public Communicator getIncoming() {
            return incoming;
        }

        public void sendMessage(Communicable message){
            outgoing.sendMessage(message);
        }

        public String getID(){
            return incoming.getID();
        }

        public CommandSender getSender() {
            return sender;
        }

        public void close()  {
            outgoing.removeMessageReceiver(incoming);
            incoming.removeMessageReceiver(outgoing);
        }
    }
}

package ton.klay.wspro.core.test.game.communication;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.communication.Communicable;
import ton.klay.wspro.core.api.game.communication.Communicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestCommandLineCommunicator implements Communicator {

    private static final Logger log = LogManager.getLogger();
    private final String id;

    List<Communicator> communication = new ArrayList<>();
    ExecutorService service = Executors.newSingleThreadExecutor();


    public TestCommandLineCommunicator(String id){

        this.id = id;
        if (id.equalsIgnoreCase("player1")) {
            service.execute(() -> {
                Scanner in = new Scanner(System.in);

                while (!service.isShutdown()) {
                    //note you will have to send one more message after the system ends to close the thread.
                    Communicable msg = CommandUtil.wrapCommunicable(in.nextLine());
                    if (!service.isShutdown())
                        sendMessage(msg);
                }
            });
        }
    }

    @Override
    public void sendMessage(Communicable message) {
        for (Communicator c : communication)
            c.receiveMessage(message);
    }

    @Override
    public void addMessageReceiver(Communicator communicator) {
        communication.add(communicator);
    }

    @Override
    public void removeMessageReceiver(Communicator communicator) {
        service.shutdownNow();
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public void receiveMessage(Communicable message) {
        System.out.println("[" + id + "] " + message.toCommunicableString());

    }
}

package to.klay.wspro.server.grpc.manual;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class CreateGameFromEncoreDecks {

    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args) {
        //get Deck ID
        Scanner in = new Scanner(System.in);

        System.out.println("EncoreDeck API Url: ");
        String url = in.nextLine();

        System.out.println("Local server port: ");
        int port = in.nextInt();
        in.nextLine();

        String gameID = ManualTestAutomatedCommands.createGame(url, port);
        System.out.println(gameID);

    }
}

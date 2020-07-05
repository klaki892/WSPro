package ton.klay.wspro.core.api.game.player;

import ton.klay.wspro.core.game.actions.PlayChoice;
import ton.klay.wspro.core.game.actions.PlayChoiceAction;
import ton.klay.wspro.core.game.actions.PlayChoiceType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PlayerControllerTest {

    //makes the default action of ending a turn or selecting the first item if no END_ACTION is provided
    public static PlayChoice defaultPlayChoiceMaker(List<PlayChoice> playChoices) {
        System.err.println(Arrays.toString(playChoices.toArray()));
        Optional<PlayChoice> endTurnAction = playChoices.stream().filter(choice -> choice.getChoiceType().equals(PlayChoiceType.CHOOSE_ACTION) && choice.getAction().equals(PlayChoiceAction.END_ACTION)).findFirst();
        return endTurnAction.orElseGet(() -> playChoices.get(0));
    }


    /**
     * Asks for input from the command line to get a player choice
     * @param playChoices
     * @return
     */
    public static PlayChoice commandLinePlayChoiceMaker(List<PlayChoice> playChoices) {
        StringBuilder sb = new StringBuilder();
        sb.append("Play Choice").append(System.lineSeparator())
                .append("-----------").append(System.lineSeparator());
        int choiceNumber = 0;
        for (PlayChoice playChoice : playChoices) {
            sb.append(String.format("#%d | %s\n", choiceNumber++, playChoice));
        }
        Scanner in = new Scanner(System.in);
        System.err.println(sb.toString());
        int choice = in.nextInt();

        return playChoices.get(choice);

    }


}
/*******************************************************************************
 *  WSPro - Trading Card Game Simulator
 *  Copyright (C) 2020  Klayton Killough
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published
 *  by the Free Software Foundation, version 3 of the License.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

package to.klay.wspro.core.api.game.player;

import to.klay.wspro.core.game.actions.PlayChoice;
import to.klay.wspro.core.game.actions.PlayChoiceAction;
import to.klay.wspro.core.game.actions.PlayChoiceType;
import to.klay.wspro.core.game.actions.PlayChooser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class TestPlayerControllers {

    //makes the default action of  selecting the first item. (Ignores exchange position
    public static List<PlayChoice> defaultPlayChoiceMaker(PlayChooser chooser) {
        ArrayList<PlayChoice> playChoices = new ArrayList<>(chooser.getChoices());

        List<PlayChoice> actualPlayChoices = playChoices.stream().filter(choice -> choice.getChoiceType() != PlayChoiceType.EXCHANGE_POSITIONS).collect(Collectors.toList());

        System.err.println(Arrays.toString(actualPlayChoices.toArray()));
        PlayChoice chosen = actualPlayChoices.get(0);
        return Collections.singletonList(chosen);
    }

    //makes the default action of ending a turn or selecting the first item if no END_ACTION is provided
    public static List<PlayChoice> doNothingPlayChoiceMaker(PlayChooser chooser) {
        ArrayList<PlayChoice> playChoices = new ArrayList<>(chooser.getChoices());

        System.err.println(Arrays.toString(playChoices.toArray()));
        Optional<PlayChoice> endTurnAction = playChoices.stream().filter(choice -> choice.getChoiceType().equals(PlayChoiceType.CHOOSE_ACTION) && choice.getAction().equals(PlayChoiceAction.END_ACTION)).findFirst();
        PlayChoice chosen = endTurnAction.orElseGet(() -> playChoices.get(0));
        return Collections.singletonList(chosen);
    }


    /**
     * Asks for input from the command line to get a player choice
     */
    public static List<PlayChoice> commandLinePlayChoiceMaker(PlayChooser chooser) {

        ArrayList<PlayChoice> playChoices = new ArrayList<>(chooser.getChoices());
        StringBuilder sb = new StringBuilder();
        sb.append("Play Choice - " + chooser.getSelectionType() + "- " + chooser.getSelectionCount()).append(System.lineSeparator())
                .append("-----------").append(System.lineSeparator());
        int choiceNumber = 0;
        for (PlayChoice playChoice : playChoices) {
            sb.append(String.format("#%d | %s\n", choiceNumber++, playChoice));
        }
        Scanner in = new Scanner(System.in);
        System.err.println(sb.toString());
        List<Integer> choices = new ArrayList<>();
        for (String s : in.nextLine().split(" ")) {
            choices.add(Integer.parseInt(s));
        }
        return choices.stream().map(playChoices::get).collect(Collectors.toList());


    }


}
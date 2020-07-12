package ton.klay.wspro.core.api.game.player;

import ton.klay.wspro.core.game.actions.PlayChoice;
import ton.klay.wspro.core.game.actions.PlayChooser;

import java.util.List;

public interface PlayerController {

    List<PlayChoice> makePlayChoice(PlayChooser chooser);

    boolean isPlayerReady();

}

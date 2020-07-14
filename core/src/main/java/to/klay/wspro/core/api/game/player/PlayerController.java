package to.klay.wspro.core.api.game.player;

import to.klay.wspro.core.game.actions.PlayChoice;
import to.klay.wspro.core.game.actions.PlayChooser;

import java.util.List;

public interface PlayerController {

    List<PlayChoice> makePlayChoice(PlayChooser chooser);

}

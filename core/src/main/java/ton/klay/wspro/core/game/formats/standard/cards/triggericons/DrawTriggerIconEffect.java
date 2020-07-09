package ton.klay.wspro.core.game.formats.standard.cards.triggericons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.abilities.components.effects.Effect;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.commands.Commands;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerCause;

public class DrawTriggerIconEffect implements Effect {

    private static final Logger log = LogManager.getLogger();
    private final PlayingCard triggerCard;

    public DrawTriggerIconEffect(PlayingCard triggerCard) {

        this.triggerCard = triggerCard;
    }

    @Override
    public void execute(Object... vars) {
        GamePlayer player = getMaster();

        //confirm they want to do effect
        boolean doAction = Commands.Utilities.getConfirmationFromPlayer(player);
        if (doAction){
            Commands.drawCard(player, TriggerCause.GAME_ACTION, this);
        }

        //todo announce effect?
    }

    @Override
    public GamePlayer getMaster() {
        return triggerCard.getMaster();
    }
}

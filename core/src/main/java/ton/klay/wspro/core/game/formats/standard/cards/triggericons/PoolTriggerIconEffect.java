package ton.klay.wspro.core.game.formats.standard.cards.triggericons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.CardOrientation;
import ton.klay.wspro.core.api.cards.abilities.components.effects.Effect;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.commands.Commands;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import ton.klay.wspro.core.game.formats.standard.zones.PlayZone;

public class PoolTriggerIconEffect implements Effect {

    private static final Logger log = LogManager.getLogger();
    private final PlayingCard triggerCard;

    public PoolTriggerIconEffect(PlayingCard triggerCard) {

        this.triggerCard = triggerCard;
    }

    @Override
    public void execute(Object... vars) {
        GamePlayer player = getMaster();
        PlayZone deck = player.getPlayArea().getPlayZone(Zones.ZONE_DECK);
        PlayZone stock = player.getPlayArea().getPlayZone(Zones.ZONE_STOCK);

        //confirm they want to do effect
        boolean doAction = Commands.Utilities.getConfirmationFromPlayer(player);
        if (doAction){
            PlayingCard topOfZoneCard = Commands.Utilities.getTopOfZoneCards(deck, 1).get(0);
            Commands.moveCard(topOfZoneCard, deck, stock, Commands.Utilities.getTopOfZoneIndex(stock),
                    CardOrientation.REST, stock.getVisibility(), TriggerCause.GAME_ACTION, this);
        }

        //todo announce pool effect?
    }

    @Override
    public GamePlayer getMaster() {
        return triggerCard.getMaster();
    }
}

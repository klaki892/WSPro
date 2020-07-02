package ton.klay.wspro.core.game.events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.CardOrientation;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.commands.Commands;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerCause;

public class RefreshPointRuleAction implements RuleAction{

    private static final Logger log = LogManager.getLogger();
    private final GamePlayer player;

    public RefreshPointRuleAction(GamePlayer player) {

        this.player = player;
    }

    @Override
    public void execute() {

        //move top card of deck to clock
        PlayZone deck = player.getPlayArea().getPlayZone(Zones.ZONE_DECK);
        PlayZone clock = player.getPlayArea().getPlayZone(Zones.ZONE_CLOCK);
        PlayingCard topCard = deck.getContents().get(Commands.Utilities.getTopOfZoneIndex(deck));
        Commands.moveCard(topCard, deck, clock, Commands.Utilities.getTopOfZoneIndex(clock), CardOrientation.STAND,
                clock.getVisibility(), TriggerCause.GAME_ACTION, player);

    }
}

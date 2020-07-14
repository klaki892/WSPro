package ton.klay.wspro.core.game.events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.CardOrientation;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.actions.PlayChoice;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.commands.Commands;
import ton.klay.wspro.core.game.formats.standard.triggers.LeveledUpTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import ton.klay.wspro.core.game.formats.standard.zones.PlayZone;

import java.util.List;
import java.util.stream.Collectors;

public class LevelUpRuleAction extends InterruptRuleAction {

    private static final Logger log = LogManager.getLogger();

    private final PlayZone clock = player.getPlayArea().getPlayZone(Zones.ZONE_CLOCK);
    private final PlayZone waitingRoom = player.getPlayArea().getPlayZone(Zones.ZONE_WAITING_ROOM);
    private final PlayZone level = player.getPlayArea().getPlayZone(Zones.ZONE_LEVEL);


    public LevelUpRuleAction(GamePlayer player){
        super(player);
    }

    @Override
    public void execute() {

        //if player is level 3, this rule action causes them to lose
//        if (level.size() == 3){
//            Commands.issueGameLoss(player, LoseConditions.LEVEL4, TriggerCause.GAME_ACTION, this);
//            return;
//        }

        //get 7 bottom cards in clock
        List<PlayingCard> bottomClockCards = Commands.Utilities.getBottomOfZoneCards(clock, 7);

        List<PlayChoice> choices = bottomClockCards.stream().map(PlayChoice::makeCardChoice).collect(Collectors.toList());

        //have player choose a card to level up
        PlayingCard levelUpCard = Commands.makeSinglePlayChoice(player, choices).getCard();

        //move card to level, rest to waiting room
        Commands.moveCard(levelUpCard, clock, level, Commands.Utilities.getTopOfZoneIndex(level),
                CardOrientation.STAND, level.getVisibility(), TriggerCause.GAME_ACTION, this);

        for (PlayingCard card : bottomClockCards) {
            if (card != levelUpCard) {
                Commands.moveCard(card, clock, waitingRoom, Commands.Utilities.getTopOfZoneIndex(waitingRoom),
                        CardOrientation.STAND, waitingRoom.getVisibility(), TriggerCause.GAME_ACTION, this);
            }
        }

        LeveledUpTrigger trigger = new LeveledUpTrigger(player, TriggerCause.GAME_ACTION, this);
        game.getTriggerManager().post(trigger);
        game.continuousTiming();
    }

}

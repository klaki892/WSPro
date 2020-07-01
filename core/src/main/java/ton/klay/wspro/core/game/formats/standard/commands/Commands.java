package ton.klay.wspro.core.game.formats.standard.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.CardOrientation;
import ton.klay.wspro.core.api.cards.GameVisibility;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.game.Duel;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.triggers.CardMovedTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerCause;

/**
 * Moves a card from one zone to another during a game
 */
public class Commands {

    private static final Logger log = LogManager.getLogger();

    /**
     * Moves a card from one zone to another
     * @return A Trigger representing the action that occurred
     */
    public static CardMovedTrigger moveCard(PlayingCard card,
                                            PlayZone sourceZone,
                                            PlayZone destinationZone,
                                            int destinationIndex,
                                            CardOrientation destinationOrientation,
                                            GameVisibility destinationVisibility,
                                            TriggerCause cause, GameEntity caller){

        PlayingCard destinationCard;
        Duel game = card.getGame();


        sourceZone.remove(card);

        if (sourceZone.getZoneName() == Zones.ZONE_STAGE && destinationZone.getZoneName() == Zones.ZONE_STAGE){
            //if on stage dont recreate card
            destinationCard = card;
            destinationCard.setFundamentalOrder(game.getNextFundamentalOrder());
        } else {
            //Re-Create card into a new zone
            card.deregister();
            destinationCard = new PlayingCard(game, card.getPaperCard(), card.getMaster(), card.getOwner());
        }

        //set states
        destinationZone.add(destinationCard, destinationIndex);
        destinationCard.setOrientation(destinationOrientation);
        destinationCard.setVisibility(destinationVisibility);

        CardMovedTrigger trigger =  new CardMovedTrigger(card,
                sourceZone,
                destinationZone,
                destinationIndex,
                destinationCard,
                destinationOrientation,
                destinationVisibility,
                cause,
                caller);

        //announce event
        game.getTriggerManager().post(trigger);

        game.interruptTiming();
        game.continuousTiming();

        return trigger;
    }

}

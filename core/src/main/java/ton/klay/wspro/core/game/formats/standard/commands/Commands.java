package ton.klay.wspro.core.game.formats.standard.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.CardOrientation;
import ton.klay.wspro.core.api.cards.GameVisibility;
import ton.klay.wspro.core.api.game.GameEntity;
import ton.klay.wspro.core.api.game.LoseConditions;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.Game;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.triggers.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Moves a card from one zone to another during a game
 */
public class Commands {

    private static final Logger log = LogManager.getLogger();

    /**
     * emits the trigger and does timing checks. (eg. Interrupt timings and Continious timings) <br/>
     * To be done after every action has been executed.
     * @param game
     * @param trigger
     */
    private static void emitAndTimings(Game game, BaseTrigger trigger){
        game.getTriggerManager().post(trigger);
        game.interruptTiming();
        game.continuousTiming();
    }


    /**
     * Moves a card from one zone to another
     * @return A Trigger representing the action that occurred
     */
    public static CardMovedTrigger moveCard(PlayingCard card, PlayZone sourceZone, PlayZone destinationZone,
                                            int destinationIndex, CardOrientation destinationOrientation,
                                            GameVisibility destinationVisibility, TriggerCause cause,
                                            GameEntity caller){

        PlayingCard destinationCard;
        Game game = card.getGame();

        boolean movingWithinStage = Zones.isOnStage(sourceZone) && Zones.isOnStage(destinationZone)
                && sourceZone.getMaster().equals(destinationZone.getMaster());

        boolean movingWithinZone = sourceZone.equals(destinationZone);
        sourceZone.remove(card);

        if (movingWithinStage){
            //if on stage dont recreate card
            destinationCard = card;
            destinationCard.setFundamentalOrder(game.getNextFundamentalOrder());
        } else if (movingWithinZone) {
            // do not update fundamental order for movement within a zone
            destinationCard = card;
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
        emitAndTimings(game,trigger);
        return trigger;
    }

    public static CardDrawnTrigger drawCard(GamePlayer player, TriggerCause cause, GameEntity caller){

        PlayZone deck = player.getPlayArea().getPlayZone(Zones.ZONE_DECK);
        PlayZone hand = player.getPlayArea().getPlayZone(Zones.ZONE_HAND);
        PlayingCard topDeckCard = deck.getContents().get(Commands.Utilities.getTopOfZoneIndex(deck)-1);

        //draw card
        CardMovedTrigger cardMovedTrigger = moveCard(topDeckCard, deck, hand, Utilities.getTopOfZoneIndex(hand), CardOrientation.STAND,
                hand.getVisibility(), cause, caller);

        CardDrawnTrigger trigger = new CardDrawnTrigger(player, cardMovedTrigger.getDestinationCard(), cause, caller);

        emitAndTimings(topDeckCard.getGame(), trigger);
        return trigger;
    }

    /**
     * A Card is discarded from hand to Waiting room.
     * @param player
     * @param card
     * @param cause
     * @param caller
     * @return
     */
    public static CardDiscardedTrigger discardCard(GamePlayer player, PlayingCard card,
                                                   TriggerCause cause, GameEntity caller){
        Game game = player.getGame();
        PlayZone hand = player.getPlayArea().getPlayZone(Zones.ZONE_HAND);
        PlayZone waitingRoom = player.getPlayArea().getPlayZone(Zones.ZONE_WAITING_ROOM);

        //move card to waiting room
        CardMovedTrigger movedTrigger = moveCard(card, hand, waitingRoom, Utilities.getTopOfZoneIndex(waitingRoom),
                CardOrientation.STAND, waitingRoom.getVisibility(), cause, caller);

        CardDiscardedTrigger trigger = new CardDiscardedTrigger(player, movedTrigger.getDestinationCard(), cause,
                caller);

        emitAndTimings(game, trigger);
        return trigger;
    }

    /**
     * randomly change the location indexes of cards in a zone. Updates the game identifer on the cards within the zone
     * @param zone
     * @param cause
     * @param caller
     * @return
     */
    public static ZoneShuffledTrigger shuffleZone(PlayZone zone, TriggerCause cause, GameEntity caller){

        Game game = zone.getMaster().getGame();

        List<PlayingCard> cards = zone.getContents();
        for (PlayingCard card : cards) {
            zone.remove(card);
            card.refreshGUID();
        }
        Collections.shuffle(cards, game.getRandom());
        for (PlayingCard card : cards) {
            zone.add(card);
        }

        ZoneShuffledTrigger trigger = new ZoneShuffledTrigger(zone, cards, cause, caller);
        emitAndTimings(game, trigger);
        return trigger;
    }

    /**
     *  Called when a player has been declared to have lost the game.
     * @param player
     * @param loseCondition
     * @param cause
     * @param caller
     * @return
     */
    public static PlayerLostTrigger issueGameLoss(GamePlayer player, LoseConditions loseCondition, TriggerCause cause,
                                                  GameEntity caller){

        //todo change stats in game to indicate this player has lost.

        PlayerLostTrigger trigger = new PlayerLostTrigger(player, loseCondition, cause, caller);
        emitAndTimings(player.getGame(), trigger);
        return trigger;
    }

    public static Optional<CardOrientedTrigger> changeCardOrientation(PlayingCard card, CardOrientation orientTo, TriggerCause cause, GameEntity caller) {
        CardOrientation orientedFrom = card.getOrientation();
        //no orientation was done.
        if (orientedFrom == orientTo) return Optional.empty();

        switch (orientTo){
            case STAND:
                if (card.canStand()) card.setOrientation(CardOrientation.STAND);
                break;
            case REST:
                if (card.canRest()) card.setOrientation(CardOrientation.REST);
                break;
            case REVERSED:
                if (card.canRest()) card.setOrientation(CardOrientation.REVERSED);
                break;
        }
        if (card.getOrientation() != orientTo) return Optional.empty();

        //orientation changed, issue event
        CardOrientedTrigger trigger = new CardOrientedTrigger(card, orientedFrom , orientTo, cause, caller);
        emitAndTimings(card.getGame(), trigger);
        return Optional.of(trigger);
    }

    public static class Utilities {
        public static int getTopOfZoneIndex(PlayZone zone){
            return zone.getContents().size();
        }

        public static int getBottomOfZoneIndex(){
            return 0;
        }
    }


}

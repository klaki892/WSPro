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

package to.klay.wspro.core.game.formats.standard.commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.CardOrientation;
import to.klay.wspro.core.api.cards.Cost;
import to.klay.wspro.core.api.cards.GameVisibility;
import to.klay.wspro.core.api.cards.abilities.AbilityKeyword;
import to.klay.wspro.core.api.game.GameEntity;
import to.klay.wspro.core.api.game.GameRuntimeException;
import to.klay.wspro.core.api.game.LoseConditions;
import to.klay.wspro.core.api.game.field.Zones;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.Game;
import to.klay.wspro.core.game.actions.PlayChoice;
import to.klay.wspro.core.game.actions.PlayChoiceAction;
import to.klay.wspro.core.game.actions.PlayChooser;
import to.klay.wspro.core.game.cards.CardType;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.CardDiscardedTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.CardDrawnTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.CardMovedTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.CardOrientedTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.CardPlayedTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.CostPaidTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.DamageProcessedTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.PlayerLostTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import to.klay.wspro.core.game.formats.standard.triggers.ZoneShuffledTrigger;
import to.klay.wspro.core.game.formats.standard.zones.PlayZone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static to.klay.wspro.core.game.formats.standard.commands.Commands.Utilities.getTopOfZoneIndex;

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
     * Moves a card from one zone to another. Handles special conditions such as move markers with its parent card.
     * @return A Trigger representing the action that occurred
     */
    public static CardMovedTrigger moveCard(PlayingCard card, PlayZone sourceZone, PlayZone destinationZone,
                                            int destinationIndex, CardOrientation destinationOrientation,
                                            GameVisibility destinationVisibility, TriggerCause cause,
                                            GameEntity caller){

        PlayingCard destinationCard;
        Game game = card.getGame();

        if (!sourceZone.contains(card)){
            log.error(String.format("Tried to move Card (%s) From Zone(%s) But card is not located there. ",
                    card.getGuid(), sourceZone.getZoneName().name()) );
            return null; //may need to throw exception instead as this is an illegal call...
        }

        boolean movingWithinStage = Zones.isOnStage(sourceZone) && Zones.isOnStage(destinationZone)
                && sourceZone.getMaster().equals(destinationZone.getMaster());

        boolean movingWithinZone = sourceZone.equals(destinationZone);
        sourceZone.remove(card);

        game.enableSimultaneousLock();
        if (movingWithinStage){
            //if on stage dont recreate card
            destinationCard = card;
            destinationCard.setFundamentalOrder(game.getNextFundamentalOrder());

            //move markers with the card
            for (PlayingCard marker : card.getMarkers()) {
                PlayZone sourceMarkerZone = card.getOwner().getPlayArea().getCorrespondingMarkerZoneOnStage(sourceZone);
                PlayZone destinationMarkerZone = card.getOwner().getPlayArea().getCorrespondingMarkerZoneOnStage(destinationZone);

                moveCard(marker, sourceMarkerZone, destinationZone, Utilities.getTopOfZoneIndex(destinationMarkerZone),
                        marker.getOrientation(), marker.getVisibility(), cause, caller);
            }
        } else if (movingWithinZone) {
            // do not update fundamental order for movement within a zone
            destinationCard = card;
        } else {
            //Re-Create card into a new zone
            card.deregister();
            destinationCard = new PlayingCard(game, card.getPaperCard(), card.getMaster(), card.getOwner());

            //move corresponding markers to waiting room
            for (PlayingCard marker : card.getMarkers()) {
                PlayZone sourceMarkerZone = card.getOwner().getPlayArea().getCorrespondingMarkerZoneOnStage(sourceZone);
                PlayZone waitingRoom = card.getOwner().getPlayArea().getPlayZone(Zones.ZONE_WAITING_ROOM);

                moveCard(marker, sourceMarkerZone, waitingRoom, Utilities.getTopOfZoneIndex(waitingRoom),
                        marker.getOrientation(), marker.getVisibility(), cause, caller);
            }

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
        game.disableSimultaneousLock();

        //announce event
        emitAndTimings(game,trigger);
        return trigger;
    }

    public static CardDrawnTrigger drawCard(GamePlayer player, TriggerCause cause, GameEntity caller){

        PlayZone deck = player.getPlayArea().getPlayZone(Zones.ZONE_DECK);
        PlayZone hand = player.getPlayArea().getPlayZone(Zones.ZONE_HAND);
        PlayingCard topDeckCard = deck.getContents().get(Commands.Utilities.getTopOfZoneIndex(deck));

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
    public static PlayerLostTrigger issuePlayerLost(GamePlayer player, LoseConditions loseCondition, TriggerCause cause,
                                                    GameEntity caller){

        //todo change stats in game to indicate this player has lost.
        player.getGame().getLosingPlayers().add(player);

        PlayerLostTrigger trigger = new PlayerLostTrigger(player, loseCondition, cause, caller);
        emitAndTimings(player.getGame(), trigger);
        return trigger;
    }

    public static Optional<CardOrientedTrigger> changeCardOrientation(PlayingCard card, CardOrientation orientTo,
                                                                      TriggerCause cause, GameEntity caller) {
        CardOrientation orientedFrom = card.getOrientation();
        //no orientation was done.
        if (orientedFrom == orientTo) return Optional.empty();

        switch (orientTo){
            case STAND:
                if (card.isStandable()) card.setOrientation(CardOrientation.STAND);
                break;
            case REST:
                if (card.isRestable()) card.setOrientation(CardOrientation.REST);
                break;
            case REVERSED:
                if (card.isRestable()) card.setOrientation(CardOrientation.REVERSED);
                break;
        }
        if (card.getOrientation() != orientTo) return Optional.empty();

        //orientation changed, issue event
        CardOrientedTrigger trigger = new CardOrientedTrigger(card, orientedFrom , orientTo, cause, caller);
        emitAndTimings(card.getGame(), trigger);
        return Optional.of(trigger);
    }

    /**
     *  Called when a player play's a card from hand or from a card effect.
     *  (such as {@link AbilityKeyword#KEYWORD_CHANGE})
     * @param player
     * @param card
     * @param cause
     * @param caller
     * @return
     */
    public static CardPlayedTrigger playCard(GamePlayer player, PlayingCard card, PlayZone sourceZone,
                                             PlayZone destinationZone, TriggerCause cause, GameEntity caller) {

        //todo reveal card

        //move the card
        CardMovedTrigger cardMovedTrigger = Commands.moveCard(card, sourceZone, destinationZone, getTopOfZoneIndex(destinationZone),
                CardOrientation.STAND, destinationZone.getVisibility(), cause, caller);

        CardPlayedTrigger trigger = new CardPlayedTrigger(player, cardMovedTrigger.getDestinationCard(), sourceZone, destinationZone, cause, caller);
        emitAndTimings(card.getGame(), trigger);
        return trigger;
    }

    /**
     * Pays a cost while preventing interrupt rule actions from occuring during the cost payment.
     * @param cost
     * @param caller
     * @return
     */
    public static CostPaidTrigger payCost(Cost cost, GameEntity caller) {
        Game game = caller.getMaster().getGame();

        //interrupt actions cant happen during paying a cost
        game.enableInterruptLock();
        cost.payCost();
        game.disableInterruptLock();

        //potential bug: if a card doesnt have a cost, we should/shouldnt emit the trigger (as there was no cost)
        CostPaidTrigger trigger = new CostPaidTrigger(TriggerCause.GAME_ACTION, caller);
        emitAndTimings(game, trigger);
        return trigger;
    }

    public static List<PlayChoice> makePlayChoice(GamePlayer player, PlayChooser chooser){

        List<PlayChoice> decision;
        boolean invalidSelectionCount;

        if (chooser.getChoices().size() <= 0){
            log.error("Asked to make a Play choice with 0 choices");
            return Collections.emptyList();
        }

        //if they made more/less selections than allowed, log and re ask the question.
        do {
            decision = player.getController().makePlayChoice(chooser);

            if (chooser.getSelectionType() == PlayChooser.SelectionType.MULTI ||chooser.getSelectionType() == PlayChooser.SelectionType.SINGLE){
                invalidSelectionCount = decision.size() != chooser.getSelectionCount();
            } else{
                invalidSelectionCount = decision.size() > chooser.getSelectionCount();
            }

            if (invalidSelectionCount) {
                log.warn(String.format("Player(%s) Made incorrect number of selections. Requested: %d Made: %d",
                                player, chooser.getSelectionCount(), decision.size() ));
            }
        } while (invalidSelectionCount);

        //todo do we announce that they have made a decision?
        return decision;
    }

    public static PlayChoice makeSinglePlayChoice(GamePlayer player, List<PlayChoice> choices){
        return makePlayChoice(player, new PlayChooser(choices)).get(0);
    }

    public static DamageProcessedTrigger dealDamage(PlayingCard sourceCard, int amount, GamePlayer receivingPlayer, TriggerCause cause, GameEntity caller) {
        PlayZone deck = receivingPlayer.getPlayArea().getPlayZone(Zones.ZONE_DECK);
        PlayZone resolution = receivingPlayer.getPlayArea().getPlayZone(Zones.ZONE_RESOLUTION);
        PlayZone waitingRoom = receivingPlayer.getPlayArea().getPlayZone(Zones.ZONE_WAITING_ROOM);
        PlayZone clock = receivingPlayer.getPlayArea().getPlayZone(Zones.ZONE_CLOCK);

        ArrayList<PlayingCard> resolutionCards = new ArrayList<>();

        boolean damageSticks = true;
        for (int i = 0; i < amount; i++) {
            //move cards from top of deck
            PlayingCard topDeck = deck.getContents().get(0);
            CardMovedTrigger cardMovedTrigger = Commands.moveCard(topDeck, deck, resolution, getTopOfZoneIndex(resolution),
                    CardOrientation.STAND, GameVisibility.VISIBLE_TO_ALL, cause, caller);
            resolutionCards.add(cardMovedTrigger.getDestinationCard());

            if (topDeck.getCardType() == CardType.CLIMAX) {
                damageSticks = false;
                break;
            }
        }
            if (damageSticks){
                //move cards to clock in the order that they went into resolution
                for (PlayingCard resolutionCard : resolutionCards) {
                    Commands.moveCard(resolutionCard, resolution, clock, getTopOfZoneIndex(clock),
                            CardOrientation.STAND, GameVisibility.VISIBLE_TO_ALL, cause, caller);
                }
            } else  {
                //move to waiting room
                for (PlayingCard resolutionCard : resolutionCards) {
                    Commands.moveCard(resolutionCard, resolution, waitingRoom, getTopOfZoneIndex(waitingRoom),
                            CardOrientation.STAND, GameVisibility.VISIBLE_TO_ALL, cause, caller);
                }
            }

        DamageProcessedTrigger trigger = new DamageProcessedTrigger(sourceCard, amount, receivingPlayer, !damageSticks, TriggerCause.GAME_ACTION, caller);
        emitAndTimings(caller.getMaster().getGame(), trigger);
        return trigger;

    }

    public static class Utilities {
        public static int getTopOfZoneIndex(PlayZone zone){
            return Math.max(0, zone.getContents().size()-1);
        }

        public static int getBottomOfZoneIndex(){
            return 0;
        }

        public static List<PlayingCard> getBottomOfZoneCards(PlayZone zone, int numCards){
            numCards = Math.min(zone.size(), numCards);
            List<PlayingCard> contents = zone.getContents();
            List<PlayingCard> retList = new ArrayList<>();

            //this loop needs to be fixed if getBottomOfZoneIndex changes
            for (int i = getBottomOfZoneIndex(); i < numCards; i++) {
                retList.add(contents.get(i));
            }
            return retList;
        }
        public static List<PlayingCard> getTopOfZoneCards(PlayZone zone, int numCards){
            numCards = Math.min(zone.size(), numCards);
            List<PlayingCard> contents = zone.getContents();
            List<PlayingCard> retList = new ArrayList<>();

            int bottomOfTop = Math.max(0, getTopOfZoneIndex(zone) - numCards);
            //this loop needs to be fixed if getBottomOfZoneIndex changes
            for (int i = bottomOfTop; i <= getTopOfZoneIndex(zone); i++) {
                retList.add(contents.get(i));
            }
            return retList;
        }

        /**
         * Asks a player for confirmation of an action by asking a Yes No Quesiton
         * @param player the one making the decision
         * @return the response from the player
         */
        public static boolean getConfirmationFromPlayer(GamePlayer player){
            //make choices
            List<PlayChoice> choices = Arrays.asList(
                    PlayChoice.makeActionChoice(PlayChoiceAction.AFFIRMATIVE),
                    PlayChoice.makeActionChoice(PlayChoiceAction.NEGATIVE)
            );
            return Commands.makeSinglePlayChoice(player, choices).getAction() == PlayChoiceAction.AFFIRMATIVE;
        }

        public static PlayZone getFacingZone(PlayZone centerStageZone){

            GamePlayer owner = centerStageZone.getOwner();
            Game game = owner.getGame();

            GamePlayer facingPlayer;
            if (game.getCurrentTurnPlayer() == owner) {
                facingPlayer = game.getNonTurnPlayer();
            } else {
                facingPlayer = game.getCurrentTurnPlayer();
            }

            switch (centerStageZone.getZoneName()) {
                case ZONE_CENTER_STAGE_LEFT:
                    return facingPlayer.getPlayArea().getPlayZone(Zones.ZONE_CENTER_STAGE_RIGHT);
                case ZONE_CENTER_STAGE_MIDDLE:
                    return facingPlayer.getPlayArea().getPlayZone(Zones.ZONE_CENTER_STAGE_MIDDLE);
                case ZONE_CENTER_STAGE_RIGHT:
                    return facingPlayer.getPlayArea().getPlayZone(Zones.ZONE_CENTER_STAGE_LEFT);
                default:
                    throw new GameRuntimeException(new IllegalArgumentException(
                            String.format("Zone (%s) is not in center stage, and we were asked to get its facing zone",
                                    centerStageZone)
                    ));
            }
        }

        public static Optional<PlayingCard> getFacingCard(PlayZone centerStageZone){

            PlayZone facingZone;
            try {
                facingZone = getFacingZone(centerStageZone);
                if (facingZone.getContents().size() <= 0)
                    return Optional.empty();
                else
                    return Optional.ofNullable(facingZone.getContents().get(0));
            } catch (GameRuntimeException ex){
                if (ex.getCause() instanceof IllegalArgumentException){
                    return Optional.empty();
                } else {
                    throw ex;
                }
            }
        }

        public static Optional<PlayingCard> getFacingCard(PlayingCard card){
            //find the card in the stage
            Optional<PlayZone> ourStageZone = getCardOnStage(card);
            if (ourStageZone.isPresent() && Zones.isOnCenterStage(ourStageZone.get())){
                return getFacingCard(ourStageZone.get());
            } else{
                return Optional.empty();
            }
        }

        public static Optional<PlayZone> getCardOnStage(PlayingCard card){
            List<PlayZone> stageZones = new ArrayList<>();
            stageZones.addAll(card.getGame().getCurrentTurnPlayer().getPlayArea().getPlayZones(Zones.ZONE_STAGE));
            stageZones.addAll(card.getGame().getNonTurnPlayer().getPlayArea().getPlayZones(Zones.ZONE_STAGE));

            for (PlayZone stageZone : stageZones) {
                if (stageZone.contains(card))
                    return Optional.of(stageZone);
            }
            return Optional.empty();
        }
    }


}

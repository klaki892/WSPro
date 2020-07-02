package ton.klay.wspro.core.game.scripting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.game.Game;
import ton.klay.wspro.core.game.actions.GameAction;
import ton.klay.wspro.core.game.actions.InvalidActionException;
import ton.klay.wspro.core.api.cards.abilities.components.AbilityConditions;
import ton.klay.wspro.core.api.game.LoseConditions;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.zones.DeckZone;
import ton.klay.wspro.core.game.formats.standard.zones.BasePlayZone;
import ton.klay.wspro.core.game.formats.standard.zones.WaitingRoomZone;

import java.util.Collections;
import java.util.List;

import static ton.klay.wspro.core.game.actions.DuelUtil.checkIfWorked;

/**
 * Class which focuses on creating utility functions for scripters to use on card based abilities and effects <br>
 *     Functions in this class which would affect the game state are passed to {@link GameAction} for actual execution, but all checks for validity are done here. <br>
*      Functions in this class which are simply used for utility are fulled performed in this class.
 */
public class ScriptingFunctions {

    private static final Logger log = LogManager.getLogger();

    private Game game;

    /**
     * Creates an instance of the Scripting Functions class so that scripts that invoke the commands are redirected to the current referenced duel
     * @param game the duel in which the actions are currently taking place
     */
    public ScriptingFunctions(Game game){
        this.game = game;
    }


    /**
     *  Moves a card from the deck zone to hand zone while firing drawing specific triggers <br>
     *      If the Deck contains no cards, it will preform a refresh (interrupt rule action) before drawing the card.
     * @return - whether the operation was successful (in drawing all of the specified number of cards or not)
     */
    public boolean drawCard(){
        return drawCards(game.getCurrentTurnPlayer(), 1);
    }

    /**
     *  Moves a card from the deck zone to hand zone while firing drawing specific triggers <br>
     *      If the Deck contains no cards, it will preform a refresh (interrupt rule action) before drawing the card.
     * @param targetPlayer - the player who the action of drawing cards will be pointed to
     * @return - whether the operation was successful (in drawing all of the specified number of cards or not)
     */
    public boolean drawCard(GamePlayer targetPlayer){
        return drawCards(targetPlayer, 1);
    }

    /**
     *  Moves cards from deck zone to hand zone while firing drawing specific triggers <br>
     *      If the Deck contains no cards, it will preform a refresh (interrupt rule action) before drawing the card.
     * @param numberOfCardsToDraw - the number of times to repeat the draw action (and triggers)
     * @return - whether the operation was successful (in drawing all of the specified number of cards or not)
     */
    public boolean drawCards(int numberOfCardsToDraw){
        return drawCards(game.getCurrentTurnPlayer(), numberOfCardsToDraw);
    }
    /**
     *  Moves cards from deck zone to hand zone while firing drawing specific triggers <br>
     *      If the Deck contains no cards, it will preform a refresh (interrupt rule action) before drawing the card.
     * @param numberOfCardsToDraw - the number of times to repeat the draw action (and triggers)
     * @param targetPlayer - the player who the action of drawing cards will be pointed to
     * @return - whether the operation was successful (in drawing all of the specified number of cards or not)
     */

    public boolean drawCards(GamePlayer targetPlayer, int numberOfCardsToDraw){
        for (int i = 0; i < numberOfCardsToDraw; i++) {
            //check if cards exist in the deck
            DeckZone deck = (DeckZone)targetPlayer.getPlayArea().getPlayZone(Zones.ZONE_DECK);
            if (deck.size() == 0){
                if (!deckReshuffle(targetPlayer))
                    throw new InvalidActionException("Deck reshuffle failed for player" + targetPlayer);
            }


            if (!moveCard(deck.peek(), deck, targetPlayer.getPlayArea().getPlayZone(Zones.ZONE_HAND)))
                return false;
            else {
                game.triggerCheck(AbilityConditions.CONDITION_ON_DRAW);
                //todo record GameACtion of Drawing a card
            }

        }
        return true;
    }

    /*todo card gains or loses, or sets something something
        - power
        - soul
        - traits
        - triggers?
        - ability
        - level
        - cost

     */
    //todo change card stance
    /*todo search for a card
        - looking at a zone for said card
        - apply a viewing filter for specific types of card (based on name, cardType, etc)
        - move the card from the zone to another zone
     */
    //todo choose card(s) from zone [with Filter Critera)

    //todo shuffle the deck
    //todo deck refresh (move all cards from waiting room to deck, sperate overloaded functions for refresh pen)

    /**
     * Preforms a Deck reshuffle by adding all cards from waiting room to deck. <br>
     *     By Default adds a refresh point to the player in which the refresh took place. <br>
     *     Also preforms check for deck-out situation.
     * @param targetPlayer - the player whom will preform the reshuffle
     * @return - whether the reshuffle was successful or not
     *@see <code>Weiss Schwarz Rule 9.2</code>
     */
    public boolean deckReshuffle(GamePlayer targetPlayer) throws LoseCondition{
        return deckReshuffle(targetPlayer, true);
    }

    /**
     * Preforms a Deck reshuffle by adding all cards from waiting room to deck. <br>
     *     By Default adds a refresh point to the player in which the refresh took place. <br>
     *     Also preforms check for deck-out situation.
     * @param addRefreshPoint - True if a refresh point should be added to the targetplayer's count.
     * @param targetPlayer - the player whom will preform the reshuffle
     * @return - whether the reshuffle was successful or not
     * @see <code>Weiss Schwarz Rule 9.2</code>
     */

    public boolean deckReshuffle(GamePlayer targetPlayer, boolean addRefreshPoint) throws LoseCondition{
        DeckZone deck = (DeckZone)targetPlayer.getPlayArea().getPlayZone(Zones.ZONE_DECK);
        WaitingRoomZone waitingRoom = (WaitingRoomZone) targetPlayer.getPlayArea().getPlayZone(Zones.ZONE_WAITING_ROOM);

        //check for lose condition
        if (deck.isEmpty() && waitingRoom.isEmpty())
            throw new LoseCondition(LoseConditions.EMPTY_DECK_AND_WAITING, targetPlayer);

        //move waiting room contents
        for (PlayingCard card : waitingRoom.getContents()) {
            deck.add(card);
        }
        waitingRoom.clear();

        //shuffle and replace deck
        List<PlayingCard> tempDeck = (deck.getContents());
        Collections.shuffle(tempDeck);
        deck.setContents(tempDeck.toArray(new PlayingCard[tempDeck.size()]));

        if (addRefreshPoint) {
            //todo add refresh point to targetplayer counter.
        }

        return true;

    }
    //todo reveal card
    // todo get zone (may need functions for each zone

    /**
     * Functions that returns a {@link BasePlayZone} reference to a zone on the game board, assumes current player is targer
     * @param zone  Name of zone to retrieve
     * @return a playzone reference to the zone in the game
     */
    public ton.klay.wspro.core.api.game.field.PlayZone getZone(Zones zone){
        return getZone(game.getCurrentTurnPlayer(), zone);
    }

    /**
     * Functions that returns a {@link BasePlayZone} reference to a zone on the game board, assumes current player is targer
     * @param targetPlayer the specific player to retreive the zone from
     * @param targetZone  Name of zone to retrieve
     * @return a playzone reference to the zone in the game
     * @throws IllegalScriptExecutionException - if an error occurs such that the zone is not findable
     *
     */
    public ton.klay.wspro.core.api.game.field.PlayZone getZone(GamePlayer targetPlayer, Zones targetZone){
        for (ton.klay.wspro.core.api.game.field.PlayZone playZone : game.getPlayingField()){
            if (playZone.getZoneName().equals(targetZone) && playZone.getOwner().equals(targetPlayer))
                return playZone;
        }

        String error = ("Unable to find zone: '" + targetZone + "' that belongs to player '" + targetPlayer + "'.");
        throw new IllegalScriptExecutionException(error);
    }


    //todo repeat action up to [condition or break]
    //todo deal damage from effect (create triggerEvent for on effect damage cancelled)
    //todo throw lose condition


    /**
     * Moves a card from one zone to another zone.
     * @param card - Card to be moved
     * @param sourceZone - Zone in which card can be found
     * @param destinationZone - zone in which we are placing card
     * @return whether the action was successful
     * @throws InvalidActionException if the action could not be completed due to errors in the command
     */
    private static boolean moveCard(PlayingCard card, ton.klay.wspro.core.api.game.field.PlayZone sourceZone, ton.klay.wspro.core.api.game.field.PlayZone destinationZone) throws InvalidActionException{
        log.debug("Moving " + card + " from " + sourceZone + " to " + destinationZone);

        //check that the source zone is where we will find the card
        if (sourceZone.contains(card)){
            checkIfWorked(sourceZone.remove(card));
            destinationZone.add(card);
            return destinationZone.contains(card);
        }
        else{
            String errMessage = "sourcezone(" + sourceZone + ") doesnt contain card: " + card;
            log.error(errMessage);
            throw new InvalidActionException(errMessage);
        }
    }

}

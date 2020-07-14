package to.klay.wspro.server.setup.finders.abilities;

import to.klay.wspro.core.api.cards.PaperCard;
import to.klay.wspro.core.game.AbilityFinder;

public interface QueryableAbilityFinder extends AbilityFinder  {

    /**
     * Reports if an ability list exists for a particular card
     * @param card the card to check for abilities
     * @return true if we could find the ability definitions for a card, false otherwise.
     */
    boolean doesScriptExist(PaperCard card);


}

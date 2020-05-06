package ton.klay.wspro.core.game.cards;

import ton.klay.wspro.core.api.game.cards.GameCard;
import ton.klay.wspro.core.api.scripting.cards.CardType;

/**
 * Class used to generate new Cards from lua based on CardType
 * @see CardType
 */
public class CardFactory {

    protected  CardFactory(){}

    public static GameCard createCard(CardType cardType){
        switch (cardType){
            case CHARACTER:
                return new CharacterCard();
            case EVENT:
                return new EventCard();
            case CLIMAX:
                return new ClimaxCard();
        }

        return null;
    }

}

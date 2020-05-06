package ton.klay.wspro.core.game.cards;


import ton.klay.wspro.core.api.scripting.cards.CardType;

public class ClimaxCard extends WeissCard {



    /**
     * Constructs a Character Card for playing.
     */
    public ClimaxCard() {
        super(CardType.CLIMAX);
        level = 0;
    }



    @Override
    public boolean validCard() {
        Object[] parameters = new Object[]{
                ID,
                description,
                color,
                affiliation,
        };
        //list checks
        if (cardTriggers.isEmpty() || abilities.isEmpty())
            return false;

        for (Object parameter : parameters)
            if (parameter == null)
                return false;

        return true;
    }

}

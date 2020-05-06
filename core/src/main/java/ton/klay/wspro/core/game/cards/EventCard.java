package ton.klay.wspro.core.game.cards;


import ton.klay.wspro.core.api.scripting.cards.CardType;

public class EventCard extends WeissCard {

    public int cost;
    public boolean backup;

    /**
     * Constructs a Character Card for playing.
     */
    public EventCard() {
        super(CardType.EVENT);
    }



    //@Override
    public boolean validCard() {
        Object[] parameters = new Object[]{
                ID,
                level,
                cost,
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

    //@Override
    public void setCost(int cost) {
        this.cost = cost;
    }

    //@Override
    public int getCost() {
        return cost;
    }

    //@Override
    public void setBackup(boolean backup) {
        this.backup = backup;
    }

    //@Override
    public boolean isBackup() {
        return backup;
    }
}

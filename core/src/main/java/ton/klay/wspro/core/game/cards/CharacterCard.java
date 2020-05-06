package ton.klay.wspro.core.game.cards;

import ton.klay.wspro.core.api.game.cards.CardStageState;
import ton.klay.wspro.core.api.scripting.cards.CardType;

import java.util.ArrayList;
import java.util.List;

public class CharacterCard extends WeissCard implements ton.klay.wspro.core.api.scripting.cards.CharacterCard {

    public int power;
    public int soulCount;
    public List<String> traits;
    public int cost;
    public boolean backup;

    public CardStageState currentStageState;
    public CardStageState previousStageState;

    /**
     * Constructs a Character Card for playing.
     */
    public CharacterCard() {
        super(CardType.CHARACTER);
        traits = new ArrayList<String>();

    }



    @Override
    public boolean validCard() {
        Object[] parameters = new Object[]{
                ID,
                level,
                cost,
                description,
                power,
                soulCount,
                color,
                affiliation,
        };
        //list checks
        if (cardTriggers.isEmpty() || traits.isEmpty() || abilities.isEmpty())
            return false;

        for (Object parameter : parameters)
            if (parameter == null)
                return false;

        return true;
    }

    @Override
    public void setCost(int cost) {
        this.cost = cost;
    }



    @Override
    public int getCost() {
        return cost;
    }

    public int getLevel() {
        return level;
    }

    public void setCurrentStageState(CardStageState currentStageState) {
        this.previousStageState = currentStageState;
        this.currentStageState = currentStageState;
    }

    @Override
    public void setBackup(boolean backup) {
        this.backup = backup;
    }

    @Override
    public boolean isBackup() {
        return backup;
    }
}

package to.klay.wspro.core.api.scripting.cards;

public interface EventCard {
    void setCost(int cost);

    int getCost();

    void setBackup(boolean backup);

    boolean isBackup();
}

package ton.klay.wspro.core.test.endtoend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.PaperCard;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

public class TestPaperCard implements PaperCard {

    private static final Logger log = LogManager.getLogger();
    private String id;
    public TestPaperCard(){
        id = "TESTCARD/" + UUID.randomUUID().toString();
    }

    @Override
    public String getEnCardName() {
        return "test";
    }

    @Override
    public String getJpCardName() {
        return "test";
    }

    @Override
    public String getCardName(String locale) {
        return "test";
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public String getIcon() {
        return "test";
    }

    @Override
    public Collection<String> getTriggerIcons() {
        return Collections.singleton("test");
    }

    @Override
    public String getCardText() {
        return "test";
    }

    @Override
    public String getCardText(String locale) {
        return "test";
    }

    @Override
    public int getPower() {
        return 0;
    }

    @Override
    public int getSoul() {
        return 0;
    }

    @Override
    public Collection<String> getTraits() {
        return Collections.singleton("test");
    }

    @Override
    public String getColor() {
        return "test";
    }

    @Override
    public String getCardType() {
        return "test";
    }

    @Override
    public String getTitleName() {
        return "test";
    }

    @Override
    public String getID() {
        return id;
    }

    @Override
    public String getRarity() {
        return "test";
    }

    @Override
    public Collection<String> getAffiliations() {
        return Collections.singleton("test");
    }
}

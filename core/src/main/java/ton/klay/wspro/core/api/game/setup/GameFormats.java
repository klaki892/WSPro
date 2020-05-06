package ton.klay.wspro.core.api.game.setup;

/**
 * General Rulesets for the game, the standard game (2-player weiss, 50 cards, etc)
 */
public enum GameFormats {

    STANDARD("standard");

    private String name;
    GameFormats(String standard) {
        this.name = standard;
    }}

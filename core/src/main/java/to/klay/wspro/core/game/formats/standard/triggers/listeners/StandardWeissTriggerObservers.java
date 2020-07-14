package to.klay.wspro.core.game.formats.standard.triggers.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.game.Game;
import to.klay.wspro.core.game.formats.standard.triggers.listeners.ruleactions.LevelUpRuleIssuer;
import to.klay.wspro.core.game.formats.standard.triggers.listeners.ruleactions.LosingVerdictRuleIssuer;
import to.klay.wspro.core.game.formats.standard.triggers.listeners.ruleactions.MultipleCharactersRuleIssuer;
import to.klay.wspro.core.game.formats.standard.triggers.listeners.ruleactions.MultipleClimaxRuleIssuer;
import to.klay.wspro.core.game.formats.standard.triggers.listeners.ruleactions.RefreshPointRuleIssuer;
import to.klay.wspro.core.game.formats.standard.triggers.listeners.ruleactions.ReshuffleRuleIssuer;

/**
 * Contains the listeners for rule actions and other known game event triggers which are used in a Standard Weiss game
 */
public class StandardWeissTriggerObservers {

    private static final Logger log = LogManager.getLogger();

    public StandardWeissTriggerObservers(Game game){
        registerGameListeners(game);
    }

    /**
     * Register the listeners for rule actions and other known game event triggers
     */
    private void registerGameListeners(Game game) {
        //rule action listeners
        new LevelUpRuleIssuer(game);
        new ReshuffleRuleIssuer(game);
        new RefreshPointRuleIssuer(game);
        new LosingVerdictRuleIssuer(game);
        new MultipleCharactersRuleIssuer(game);
        new MultipleClimaxRuleIssuer(game);

        //event listeners
        new ClockFullIssuer(game);
        new DeckEmptyIssuer(game);
    }

}

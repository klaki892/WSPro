package ton.klay.wspro.core.api.cards;

import ton.klay.wspro.core.api.game.IllegalGameState;

/**
 * The Component of a {@link Cost} which performs the actions needed to fulfill a cost. <br/>
 * When a cost has been paid a corresponding
 * {@link ton.klay.wspro.core.game.formats.standard.triggers.TriggerName#COST_PAID} should be emitted
 *
 */
public interface PayCost  {
    void execute() throws IllegalGameState;
}

package ton.klay.wspro.core.game.actions;

import com.google.common.eventbus.EventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.commands.Commands;
import ton.klay.wspro.core.game.formats.standard.phases.TurnPhase;
import ton.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.CardMovedTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.PhaseStartedTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.listeners.TriggerableAbilityListener;

import java.util.Optional;

/**
 * Represents the altercation between two cards during battle
 */
public class Combat {

    private static final Logger log = LogManager.getLogger();
    private final AttackType attackType;
    private  PlayingCard attackingCharacter;
    private  PlayingCard defendingCharacter;

    public Combat(AttackType attackType, PlayingCard attackingCharacter) {
        this.attackType = attackType;
        this.attackingCharacter = attackingCharacter;
        this.defendingCharacter = Commands.Utilities.getFacingCard(attackingCharacter).orElse(null);
    }

    public void setBattleStates(){

        //check for movement of attacking character and defending character (if exists)
        //if movement, remove state of attacked or defending
        TriggerableAbilityListener combatListener = new TriggerableAbilityListener() {
            EventBus eventBus;
            @Override
            public void triggerReceived(BaseTrigger trigger) {
                //if card moved
                if (trigger instanceof CardMovedTrigger) {
                    CardMovedTrigger movedTrigger = (CardMovedTrigger) trigger;

                    PlayingCard movedCard = movedTrigger.getSourceCard();

                    if (attackingCharacter != null && movedCard == attackingCharacter){
                        attackingCharacter = null;
                    }
                    if (defendingCharacter != null & movedCard == defendingCharacter){
                        defendingCharacter = null;
                    }
                }
                //remove this combat listener when combat is over
                if (trigger instanceof PhaseStartedTrigger){
                    PhaseStartedTrigger phaseStartedTrigger = (PhaseStartedTrigger) trigger;
                    if (phaseStartedTrigger.getPhase() == TurnPhase.ATTACK_DECLARATION_STEP)
                        deregister();
                }
            }

            @Override
            public void register(EventBus eventBus) {
                eventBus.register(this);
                this.eventBus = eventBus;
            }

            @Override
            public void deregister() {
                eventBus.unregister(this);
            }
        };

        combatListener.register(attackingCharacter.getGame().getTriggerManager());
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public Optional<PlayingCard> getAttackingCharacter() {
        return Optional.ofNullable(attackingCharacter);
    }

    public void setAttackingCharacter(PlayingCard attackingCard) {
        this.attackingCharacter = attackingCard;
    }

    public Optional<PlayingCard> getDefendingCharacter() {
        return Optional.ofNullable(defendingCharacter);
    }

    public void setDefendingCharacter(PlayingCard defendingCharacter) {
        this.defendingCharacter = defendingCharacter;
    }
}

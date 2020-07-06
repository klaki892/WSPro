package ton.klay.wspro.core.game.formats.standard.phases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.actions.AttackType;
import ton.klay.wspro.core.game.actions.Combat;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.commands.Commands;
import ton.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.EndOfAttackTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerCause;

import java.util.Optional;

public class DamageStep extends BasePhase  {

    Logger log = LogManager.getLogger();

    public DamageStep(PhaseHandler phaseHandler, GamePlayer turnPlayer) {
        super(phaseHandler, turnPlayer, TurnPhase.DAMAGE_STEP);
    }

    @Override
    public void startPhase() {
        super.startPhase();

        Combat combat = phaseHandler.getCombat();
        Optional<PlayingCard> attackingCharacter = combat.getAttackingCharacter();
        if (attackingCharacter.isPresent()){
            int soul = attackingCharacter.get().getSoul();
            Commands.dealDamage(attackingCharacter.get(), soul,
                    phaseHandler.getNonTurnPlayer(), TriggerCause.GAME_ACTION, this);
        }
        game.checkTiming();

        if (combat.getAttackType() == AttackType.FRONTAL){
            phaseHandler.setNextPhase(TurnPhase.BATTLE_STEP);
        } else {
            //declare end of attack, return to declaration step
            BaseTrigger trigger = new EndOfAttackTrigger(turnPlayer, TriggerCause.GAME_ACTION, this);
            triggerSystem.post(trigger);
            game.continuousTiming();
            game.interruptTiming();
            game.checkTiming();
            phaseHandler.setNextPhase(TurnPhase.ATTACK_DECLARATION_STEP);
        }
    }
}

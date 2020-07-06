package ton.klay.wspro.core.game.formats.standard.phases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ton.klay.wspro.core.api.cards.CardOrientation;
import ton.klay.wspro.core.api.game.field.PlayZone;
import ton.klay.wspro.core.api.game.field.Zones;
import ton.klay.wspro.core.api.game.player.GamePlayer;
import ton.klay.wspro.core.game.actions.*;
import ton.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import ton.klay.wspro.core.game.formats.standard.commands.Commands;
import ton.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;
import ton.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import ton.klay.wspro.core.game.formats.standard.triggers.WillAttackTrigger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AttackDeclarationStep extends BasePhase  {

    Logger log = LogManager.getLogger();

    public AttackDeclarationStep(PhaseHandler phaseHandler, GamePlayer turnPlayer) {
        super(phaseHandler, turnPlayer, TurnPhase.ATTACK_DECLARATION_STEP);
    }

    @Override
    public void startPhase() {
        super.startPhase();

        List<PlayChoice> attackChoices = getAttackChoices();

        //if no more attackChoices, attack phase is over
        if (attackChoices.size() <= 0){
            phaseHandler.setNextPhase(TurnPhase.ENCORE_STEP);
            return;
        }

        //add the ability to do nothing
        attackChoices.add(PlayChoice.makeActionChoice(PlayChoiceAction.END_ACTION));

        PlayChoice choice = Commands.makeSinglePlayChoice(turnPlayer, attackChoices);
        if (choice.getChoiceType() == PlayChoiceType.CHOOSE_ACTION) {
            phaseHandler.setNextPhase(TurnPhase.ENCORE_STEP);
            return;
        }
        AttackPositionPair attackChoice = choice.getAttackPositionPair();
        PlayingCard attackingCard = attackChoice.getZone().getContents().get(0);

        BaseTrigger trigger = new WillAttackTrigger(attackingCard, attackChoice.getAttackType(), TriggerCause.GAME_ACTION, this);
        triggerSystem.post(trigger);
        game.continuousTiming();
        game.interruptTiming();

        phaseHandler.setAttackedThisTurn(true);

        //start combat
        Combat combat = new Combat(attackChoice.getAttackType(), attackingCard);

        //todo replacement action eligible (changes attacktype and target)
        phaseHandler.setCombat(combat);

        if (attackChoice.getAttackType() == AttackType.SIDE){

            //todo minus soul by level of facing character til end of turn
        }  else if (attackChoice.getAttackType() == AttackType.DIRECT){
            //todo add a soul to the character til end of turn
        }

        //rest the attacker
        Commands.changeCardOrientation(attackingCard, CardOrientation.REST, TriggerCause.GAME_ACTION, this);

        phaseHandler.setNextPhase(TurnPhase.TRIGGER_STEP);
        game.checkTiming();
    }

    private List<PlayChoice> getAttackChoices() {
        Collection<PlayZone> centerStageZones = turnPlayer.getPlayArea().getPlayZones(Zones.ZONE_CENTER_STAGE);
        List<PlayChoice> attackChoices = new ArrayList<>();

        //if it's the first turn and they've already attacked, they cannot select any attack choices
        if (phaseHandler.didAttackThisTurn() && phaseHandler.getTurnNumber() == 1) {
            return Collections.emptyList();
        }

        //get all attack choices
        for (PlayZone zone :centerStageZones) {
            if (zone.size() <= 0) continue;
            PlayingCard card = zone.getContents().get(0);
            boolean hasCardFacing = Commands.Utilities.getFacingCard(zone).isPresent();

            if (card.getOrientation() != CardOrientation.STAND) continue;

            if (hasCardFacing) {
                if (card.canFrontalAttack()) {
                    attackChoices.add(PlayChoice.makeAttackChoice(new AttackPositionPair(AttackType.FRONTAL, zone)));
                }
                if (card.canSideAttack()) {
                    attackChoices.add(PlayChoice.makeAttackChoice(new AttackPositionPair(AttackType.SIDE, zone)));
                }
            } else {
                if (card.canDirectAttack()) {
                    attackChoices.add(PlayChoice.makeAttackChoice(new AttackPositionPair(AttackType.DIRECT, zone)));
                }
            }
        }
        return attackChoices;
    }

}

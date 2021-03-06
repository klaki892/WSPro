/*******************************************************************************
 *  WSPro - Trading Card Game Simulator
 *  Copyright (C) 2020  Klayton Killough
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published
 *  by the Free Software Foundation, version 3 of the License.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

package to.klay.wspro.core.game.formats.standard.phases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import to.klay.wspro.core.api.cards.CardOrientation;
import to.klay.wspro.core.api.cards.abilities.components.effects.ContinuousEffect;
import to.klay.wspro.core.api.game.GameRuntimeException;
import to.klay.wspro.core.api.game.field.Zones;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.actions.AttackPositionPair;
import to.klay.wspro.core.game.actions.AttackType;
import to.klay.wspro.core.game.actions.Combat;
import to.klay.wspro.core.game.actions.PlayChoice;
import to.klay.wspro.core.game.actions.PlayChoiceAction;
import to.klay.wspro.core.game.actions.PlayChoiceType;
import to.klay.wspro.core.game.effects.AttackTypeSoulChangeEffect;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.commands.Commands;
import to.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerName;
import to.klay.wspro.core.game.formats.standard.triggers.WillAttackTrigger;
import to.klay.wspro.core.game.formats.standard.zones.PlayZone;

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



        phaseHandler.setAttackedThisTurn(true);

        //start combat

        Combat combat = new Combat(attackChoice.getAttackType(), attackingCard);
        phaseHandler.setCombat(combat);

        //replace us announcing the attack, as the attack type (and defender might
        // change (e.g. great performance)
        if (!game.getTimingManager().replaceableAction(TriggerName.ATTACK_SETUP)){
            BaseTrigger trigger = new WillAttackTrigger(combat.getAttackingCharacter().get(), combat.getAttackType(),
                    TriggerCause.GAME_ACTION, this);
            triggerSystem.post(trigger);
            game.continuousTiming();
            game.interruptTiming();
        }

        combat.setBattleStates();
        PlayingCard defendingCard = combat.getDefendingCharacter().orElse(null);

        if (attackChoice.getAttackType() == AttackType.SIDE){
            if (defendingCard == null) {
                throw new GameRuntimeException(new IllegalStateException("Side attack declared with no defending character"));
            }

            //subtract soul based on defending level
            ContinuousEffect effect = new AttackTypeSoulChangeEffect(
                    attackingCard, AttackType.SIDE, phaseHandler.getTurnNumber(), defendingCard.getLevel());
            game.getTimingManager().add(effect);

        } else if (attackChoice.getAttackType() == AttackType.DIRECT){
            //add a soul to the character til end of turn
            game.getTimingManager().add(new AttackTypeSoulChangeEffect(attackingCard, AttackType.DIRECT,
                    phaseHandler.getTurnNumber(), 1));
        }

        //rest the attacker
        Commands.changeCardOrientation(combat.getAttackingCharacter().get(), CardOrientation.REST, TriggerCause.GAME_ACTION, this);

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
                if (card.isFrontalAttackCapable()) {
                    attackChoices.add(PlayChoice.makeAttackChoice(new AttackPositionPair(AttackType.FRONTAL, zone)));
                }
                if (card.isSideAttackCapable()) {
                    attackChoices.add(PlayChoice.makeAttackChoice(new AttackPositionPair(AttackType.SIDE, zone)));
                }
            } else {
                if (card.isDirectAttackCapable()) {
                    attackChoices.add(PlayChoice.makeAttackChoice(new AttackPositionPair(AttackType.DIRECT, zone)));
                }
            }
        }
        return attackChoices;
    }

    private void defaultCombatSetup(){

    }

}

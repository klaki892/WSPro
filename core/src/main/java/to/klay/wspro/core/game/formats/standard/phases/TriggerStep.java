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
import to.klay.wspro.core.api.cards.GameVisibility;
import to.klay.wspro.core.api.game.GameRuntimeException;
import to.klay.wspro.core.api.game.field.Zones;
import to.klay.wspro.core.api.game.player.GamePlayer;
import to.klay.wspro.core.game.TimingManager;
import to.klay.wspro.core.game.actions.AttackType;
import to.klay.wspro.core.game.formats.standard.cards.PlayingCard;
import to.klay.wspro.core.game.formats.standard.cards.triggericons.ChoiceTriggerIconEffect;
import to.klay.wspro.core.game.formats.standard.cards.triggericons.ComebackTriggerIconEffect;
import to.klay.wspro.core.game.formats.standard.cards.triggericons.DrawTriggerIconEffect;
import to.klay.wspro.core.game.formats.standard.cards.triggericons.GateTriggerIconEffect;
import to.klay.wspro.core.game.formats.standard.cards.triggericons.PoolTriggerIconEffect;
import to.klay.wspro.core.game.formats.standard.cards.triggericons.ReturnTriggerIconEffect;
import to.klay.wspro.core.game.formats.standard.cards.triggericons.ShotTriggerIconEffect;
import to.klay.wspro.core.game.formats.standard.cards.triggericons.SoulTriggerIconEffect;
import to.klay.wspro.core.game.formats.standard.cards.triggericons.StandbyTriggerIconEffect;
import to.klay.wspro.core.game.formats.standard.cards.triggericons.TreasureTriggerIconEffect;
import to.klay.wspro.core.game.formats.standard.commands.Commands;
import to.klay.wspro.core.game.formats.standard.triggers.BaseTrigger;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCause;
import to.klay.wspro.core.game.formats.standard.triggers.TriggerCheckedTrigger;
import to.klay.wspro.core.game.formats.standard.zones.PlayZone;

public class TriggerStep extends BasePhase  {

    Logger log = LogManager.getLogger();

    public TriggerStep(PhaseHandler phaseHandler, GamePlayer turnPlayer) {
        super(phaseHandler, turnPlayer, TurnPhase.TRIGGER_STEP);
    }

    @Override
    public void startPhase() {
        super.startPhase();

        if (phaseHandler.getCombat().getAttackType() == AttackType.FRONTAL){
            phaseHandler.setNextPhase(TurnPhase.COUNTER_STEP);
        } else {
            phaseHandler.setNextPhase(TurnPhase.DAMAGE_STEP);
        }

        PlayZone deck = turnPlayer.getPlayArea().getPlayZone(Zones.ZONE_DECK);
        PlayZone resolution = turnPlayer.getPlayArea().getPlayZone(Zones.ZONE_RESOLUTION);
        PlayZone stock = turnPlayer.getPlayArea().getPlayZone(Zones.ZONE_STOCK);


        //trigger check
        PlayingCard triggerCard = Commands.Utilities.getTopOfZoneCards(deck, 1).get(0);
        PlayingCard triggerCardInReso = Commands.moveCard(triggerCard, deck, resolution, Commands.Utilities.getTopOfZoneIndex(resolution),
                CardOrientation.STAND, GameVisibility.VISIBLE_TO_ALL, TriggerCause.GAME_ACTION, this).getDestinationCard();

        //noinspection OptionalGetWithoutIsPresent
        applyTriggerIcons(triggerCard, phaseHandler.getCombat().getAttackingCharacter().get());
        BaseTrigger trigger = new TriggerCheckedTrigger(triggerCardInReso, TriggerCause.GAME_ACTION, this);
        triggerSystem.post(trigger);
        game.continuousTiming();
        game.interruptTiming();
        game.checkTiming();


        //put in stock if it still exists (ex: it wasn't returned by icon effect)
        if (resolution.size() == 1) {
            Commands.moveCard(triggerCardInReso, resolution, stock, Commands.Utilities.getTopOfZoneIndex(stock),
                    CardOrientation.REST, GameVisibility.HIDDEN, TriggerCause.GAME_ACTION, this);
        }
        game.checkTiming();
    }

    private void applyTriggerIcons(PlayingCard triggerCard, PlayingCard attackingCard) {
        TimingManager timingManager = game.getTimingManager();
        triggerCard.getTriggerIcons().forEach(icon -> {
            switch (icon){
                case NONE:
                    break;
                case SOUL:
                    timingManager.add(new SoulTriggerIconEffect(triggerCard, attackingCard));
                    break;
                case RETURN:
                    new ReturnTriggerIconEffect(triggerCard).execute();
                    break;
                case POOL:
                    new PoolTriggerIconEffect(triggerCard).execute();
                    break;
                case COMEBACK:
                    new ComebackTriggerIconEffect(triggerCard).execute();
                    break;
                case DRAW:
                    new DrawTriggerIconEffect(triggerCard).execute();
                    break;
                case SHOT:
                    new ShotTriggerIconEffect(triggerCard, attackingCard).execute();
                    break;
                case TREASURE:
                    new TreasureTriggerIconEffect(triggerCard).execute();
                    break;
                case GATE:
                    new GateTriggerIconEffect(triggerCard).execute();
                    break;
                case STANDBY:
                    StandbyTriggerIconEffect.execute(triggerCard).execute();
                    break;
                case CHOICE:
                    new ChoiceTriggerIconEffect(triggerCard).execute();
                    break;
                default:
                    throw new GameRuntimeException(new IllegalStateException("Unsupported Trigger Icon: " + icon));
            }
        });
    }

}

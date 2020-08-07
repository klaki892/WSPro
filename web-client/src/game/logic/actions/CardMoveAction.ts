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

import {
    ProtoCardMovedTrigger,
    ProtoPlayingCard,
    ProtoPlayZone,
    ProtoZones
} from "../../grpc/generated/ProtoEntities_pb";
import GameAction from "./GameAction";
import Game from "../Game";
import PlayActionUtilities from "../../view/PlayActionUtilities";
import ProtoUtils from "../../grpc/ProtoUtils";
import CardViewAnimations from "../../view/CardViewAnimations";

export default class CardMoveAction implements GameAction{
    private readonly trigger: ProtoCardMovedTrigger;

    isExecuting: boolean = false;

    constructor(trigger : ProtoCardMovedTrigger) {
        this.trigger = trigger;
    }

    perform(game: Game, onComplete: Function): void {
        this.isExecuting = true;

        let sourceCard = this.trigger.getSourcecard() as ProtoPlayingCard;
        let destinationCard = this.trigger.getDestinationcard() as ProtoPlayingCard;
        let sourceZone = this.trigger.getSourcezone() as ProtoPlayZone;
        let destinationZone = this.trigger.getDestinationzone() as ProtoPlayZone;
        let destinationIndex = this.trigger.getDestinationindex();
        let destinationOrientation = this.trigger.getDestinationorientation();

        //get the card instance on our end
        let gameSourceZone = ProtoUtils.FromProto.getZone(sourceZone, game);

        let card;

        if (sourceZone.getZonename() === ProtoZones.ZONE_DECK){
            console.log("Moving card " + sourceCard.getId() + " - " + destinationCard.getGuid() + "from " + ProtoUtils.FromProto.parseZoneName(sourceZone.getZonename()) + " to " + ProtoUtils.FromProto.parseZoneName(destinationZone.getZonename()) )

            //generate new card
            card = ProtoUtils.FromProto.createNewCard(destinationCard, sourceZone, game);
        } else { //get existing card
            console.log("Moving card " + sourceCard.getId() + " - " + sourceCard.getGuid() + "from " + ProtoUtils.FromProto.parseZoneName(sourceZone.getZonename()) + " to " + ProtoUtils.FromProto.parseZoneName(destinationZone.getZonename()) )
            let playCard = gameSourceZone.getContents().find(value => value.guid === sourceCard.getGuid());

            if (playCard !== undefined) {
                card = playCard;
                //update the card guid
                card.guid = destinationCard.getGuid();
            } else {
                throw new Error("we were told to move a card(" + sourceCard.getId() + " - " + sourceCard.getGuid() +  ") that didnt exist in the source zone ("+ ProtoUtils.FromProto.parseZoneName(sourceZone.getZonename()) +").");
            }
        }

        //move the card to the new location
        let gameDestinationZone = ProtoUtils.FromProto.getZone(destinationZone, game);
        //remove the card from the existing zone
        gameSourceZone.remove(card);

        //add the card to the new zone
        gameDestinationZone.add(card);
        console.log(this.trigger);


        //play animation
        //FIXME flipping isnt this simple, need to look at the actual states
        let doFlip = sourceCard.getVisibility() !== destinationCard.getVisibility();
        let anim = CardViewAnimations.moveCardAnim(card.view, gameDestinationZone.view.getCardPosition(destinationIndex), doFlip).timeScale(1.5);

        PlayActionUtilities.DoAnimateAndOnComplete(anim, () => {
            this.isExecuting = false;
            onComplete();
        })
    }




}
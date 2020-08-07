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

import PlayCardView from "./PlayCardView";
import PlayZone from "../logic/field/PlayZone";
import CardViewAnimations from "./CardViewAnimations";
import Game from "../logic/Game";

export default class PlayActionUtilities {

    static moveCard(card: PlayCardView, toZone: PlayZone){

        let viewMovement = toZone.view.getNextCardPosition();

        //remove the card from the existing zone

        //add the card to the new zone

        //play animation

        //flip = previous visibility != incoming visibility
        CardViewAnimations.moveCardAnim(card, viewMovement, true).timeScale(1.5).play(0);

    }

    static DoAnimateAndOnComplete(animation: GSAPTimeline, callback: Function){
        animation.eventCallback('onComplete', callback());
        animation.play();
    }

    static getStageView(game : Game) : PIXI.Container {
        return game.view.getPixiApp().stage;
    }
}
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
import {gsap} from "gsap";
import {Point} from "pixi.js";

export default class CardViewAnimations {

    static flipCardAnim(card: PlayCardView) : GSAPTimeline{

        let nextFace = card.texture === card.frontImage
            ? card.backImage : card.frontImage;


        let t1 = gsap.timeline({
            autoRemoveChildren: true,

        });

        t1.to(card, {
            pixi: {
                width: 0,
                height: PlayCardView.baseHeight * 1.1
            },
            duration: .2,
            onComplete: args => card.texture = nextFace

        })
            .to(card,{
                pixi: {
                    width: PlayCardView.baseWidth,
                    height: PlayCardView.baseHeight
                },
                duration: .2,
            } );

        return t1;
    }

    /**
     *  Moves a card to a position specified by the zone, optionally flipping the card during the movement
     * @param card
     * @param newPosition
     * @param flip
     */
    static moveCardAnim(card: PlayCardView, newPosition: Point, flip: boolean) : GSAPTimeline {
        let moveTimeline = gsap.timeline();

        //get the position the zone wants us to place the card

        //generate the movement
        moveTimeline.to(card, {duration: 1, x: newPosition.x, y: newPosition.y});

        let anim = gsap.timeline({
            autoRemoveChildren: true,
            paused: true
        });

        anim.add(moveTimeline, 0);
        if (flip){
            let flipAnim = this.flipCardAnim(card);
            flipAnim.timeScale(1);
            anim.add(flipAnim, 0.2);
        }

        return anim;
    }

}
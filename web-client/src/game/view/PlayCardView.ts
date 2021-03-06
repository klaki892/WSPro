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

import * as PIXI from "pixi.js";
import CardViewAnimations from "./CardViewAnimations";
import PlayCard from "../logic/PlayCard";

const baseColor = 0x000000;
const activeColor = 0xFFFFFF;
const width = 56;
const height = 79;


export default class PlayCardView extends  PIXI.Sprite {
    frontImage: PIXI.Texture;
    backImage: PIXI.Texture;
    graphic: PIXI.Graphics;
    card: PlayCard;

    static readonly baseWidth = width;
    static readonly baseHeight = height;


    constructor(card: PlayCard) {
        super();
        this.card = card;
        this.frontImage = PlayCardView.getFrontImage(card);
        this.backImage = PlayCardView.getBackImage(card);
        this.graphic = new PIXI.Graphics();
        this.addChild(this.graphic);
        this.drawCard();
        this.initCard();
    }

    private static getFrontImage(card: PlayCard) : PIXI.Texture {
        //todo
        return card.game.view.getPixiApp().loader.resources["testCardFront"].texture;
    }

    private static getBackImage(card: PlayCard) : PIXI.Texture {
        //todo
        return card.game.view.getPixiApp().loader.resources["testCardBack"].texture;
    }


    private drawCard() {
        this.width = width;
        this.height = height;
        this.texture = this.backImage;
        this.anchor.set(.5);
        this.addChild(this.graphic);
        this.graphic.scale.set(1,1);
        console.log("Card Scale: " + this.scale.x + " " + this.scale.y);
    }

    flipCard(){

        let flipCardAnim = CardViewAnimations.flipCardAnim(this);
        this.interactive = false;
        flipCardAnim.timeScale(2)
        flipCardAnim.play();
        this.interactive = true;
    }

    private initCard() {
        this.interactive = true;
        // this.interactiveChildren = true;
        // this.on('pointerover', this.onCardHover)
        // this.on('pointerout', this.onCardOut)
        this.on('pointertap', this.flipCard);

    }

    private onCardHover() {
        let newX = this.scale.x * 1.25;
        let newy = this.scale.y * 1.25;
        this.scale.set(newX, newy);
        console.log(this.width +  " width & " + this.height + " height");
    }


    private onCardOut() {
        let oldX = this.scale.x / 1.25;
        let oldY = this.scale.y / 1.25;
        this.scale.set(oldX, oldY);
        console.log(this.width +  " width & " + this.height + " height");
    }

}
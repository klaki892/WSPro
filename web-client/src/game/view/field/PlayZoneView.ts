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

import PlayZone from "../../logic/field/PlayZone";
import * as PIXI from "pixi.js";
import {Point, SCALE_MODES} from "pixi.js";


const baseColor = 0x000000;
const activeColor = 0xFFFFFF;
const width = 75;
const height = 87;

export default class PlayZoneView extends PIXI.Sprite{
    private playZone: PlayZone;
    private app : PIXI.Application

    constructor(playZone: PlayZone) {
        super();
        this.playZone = playZone;
        this.app = this.playZone.owner.game.view.getPixiApp();

        this.drawZone();
        this.anchor.set(.5, .5);
    }

    private drawZone() {

        let graphic = new PIXI.Graphics();

        graphic.beginFill(baseColor, 0.7);
        graphic.lineStyle(4, activeColor, 1);
        graphic.drawRect(0,0, width, height);
        graphic.endFill();
        graphic.interactive = true;

        let zoneNameTextStyle = new PIXI.TextStyle();
        zoneNameTextStyle.fontSize = "11pt";
        zoneNameTextStyle.fill = activeColor;
        zoneNameTextStyle.wordWrap = true;
        zoneNameTextStyle.wordWrapWidth = this.width;
        let zoneNameText = new PIXI.Text(this.playZone.zoneName, zoneNameTextStyle);

        zoneNameText.position.x = (this.width / 2) - (zoneNameText.width / 2);
        zoneNameText.position.y = (this.height / 2) - (zoneNameText.height / 2);
        this.addChild(zoneNameText);

        this.texture = this.app.renderer.generateTexture(graphic, SCALE_MODES.NEAREST, 2);

        this.interactive = true;
        this.on('pointerover', this.focusZone);
        this.on('pointerout', this.unfocusZone)
    }


    private focusZone() {
        let graphic = new PIXI.Graphics();
        graphic.clear();
        graphic.beginFill(activeColor, 0.7);
        graphic.lineStyle(4, baseColor, 1);
        graphic.drawRect(0,0, width, height);
        graphic.endFill();
        let texture = this.app.renderer.generateTexture(graphic, SCALE_MODES.NEAREST, 2);
        this.texture = texture;

    }
    
    private unfocusZone(){
        let graphic = new PIXI.Graphics();
        graphic.clear();
        graphic.beginFill(baseColor, 0.7);
        graphic.lineStyle(4, activeColor, 1);
        graphic.drawRect(0,0, width, height);
        graphic.endFill();
        let texture = this.app.renderer.generateTexture(graphic, SCALE_MODES.NEAREST, 2);
        this.texture = texture;
    }

    /**
     * Obtains the next Point where a new card should be placed.
     * By Default this is the center of the zone.
     */
    getNextCardPosition() : PIXI.Point {
        //TODO: cant use global position due to rescaling, find way to calcualte stage positions relative to stage
        let x = this.getGlobalPosition().x //+ (this.width / 2);
        // let x = this.x + (this.width / 2);
        let y = this.getGlobalPosition().y //+ (this.height/2);
        // let y = this.y + (this.height/2);
        return new Point(x, y);
    }
    getCardPosition(index: number) : PIXI.Point{
        if (index > (this.playZone.size())) {
            return this.getNextCardPosition();
        }
        //todo FIXME this is meant for specific positioning
        // get the specified position, but move all cards after it when this is called?
        return this.getNextCardPosition();

    }
}
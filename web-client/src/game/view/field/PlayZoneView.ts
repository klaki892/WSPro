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

        let texture = this.app.renderer.generateTexture(graphic, SCALE_MODES.NEAREST, 2);

        this.texture = texture;

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
}
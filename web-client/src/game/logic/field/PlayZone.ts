import {ZoneName} from "./ZoneName";
import * as PIXI from "pixi.js";

const baseColor = 0x000000;
const activeColor = 0xFFFFFF;
const width = 150;
const height = 175;
// const baseZoneStyle = new PIXI.FillStyle();
// baseZoneStyle.color = baseColor;
// baseZoneStyle.alpha = 0.7;
//
// const activeZoneStyle = new PIXI.FillStyle();
// activeZoneStyle.color = activeColor;
// activeZoneStyle.alpha = 0.7;


export default class PlayZone extends PIXI.Graphics{
    private zoneName: ZoneName;
    graphic: PIXI.Graphics;


    constructor(zoneName: ZoneName) {
        super();
        this.zoneName = zoneName;
        this.graphic = this;

        this.drawZone();
    }

    private drawZone() {
        this.graphic.beginFill(baseColor, 0.7);
        this.graphic.lineStyle(4, activeColor, 1);
        this.graphic.drawRect(0,0, width, height);
        this.graphic.endFill();
        this.interactive = true;

        let zoneNameTextStyle = new PIXI.TextStyle();
        zoneNameTextStyle.fill = activeColor;
        zoneNameTextStyle.wordWrap = true;
        zoneNameTextStyle.wordWrapWidth = this.graphic.width;
        let zoneNameText = new PIXI.Text(this.zoneName, zoneNameTextStyle);

        zoneNameText.position.x = (this.graphic.width / 2) - (zoneNameText.width / 2);
        zoneNameText.position.y = (this.graphic.height / 2) - (zoneNameText.height / 2);
        this.graphic.addChild(zoneNameText);
        this.interactive = true;
        this.graphic.on('pointerover', this.onZoneHover)

        this.graphic.on('pointerout', this.onZoneOut)
    }

    private onZoneHover() {
        this.graphic.clear();
        this.graphic.beginFill(activeColor, 0.7);
        this.graphic.lineStyle(4, baseColor, 1);
        this.graphic.drawRect(0,0, width, height);
        this.graphic.endFill();
        console.log(this.zoneName + " onHover!")

    }


    private onZoneOut() {
        this.graphic.clear();
        this.graphic.beginFill(baseColor, 0.7);
        this.graphic.lineStyle(4, activeColor, 1);
        this.graphic.drawRect(0,0, width, height);
        this.graphic.endFill();
    }
}
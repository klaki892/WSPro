import PlayZone from "../../logic/field/PlayZone";
import * as PIXI from "pixi.js";


const baseColor = 0x000000;
const activeColor = 0xFFFFFF;
const width = 150;
const height = 175;

export default class PlayZoneView extends PIXI.Graphics{
    private playZone: PlayZone;

    constructor(playZone: PlayZone) {
        super();
        this.playZone = playZone;
        this.drawZone();
    }

    private drawZone() {
        this.beginFill(baseColor, 0.7);
        this.lineStyle(4, activeColor, 1);
        this.drawRect(0,0, width, height);
        this.endFill();
        this.interactive = true;

        let zoneNameTextStyle = new PIXI.TextStyle();
        zoneNameTextStyle.fill = activeColor;
        zoneNameTextStyle.wordWrap = true;
        zoneNameTextStyle.wordWrapWidth = this.width;
        let zoneNameText = new PIXI.Text(this.playZone.zoneName, zoneNameTextStyle);

        zoneNameText.position.x = (this.width / 2) - (zoneNameText.width / 2);
        zoneNameText.position.y = (this.height / 2) - (zoneNameText.height / 2);
        this.addChild(zoneNameText);
        this.interactive = true;

        this.on('pointerover', this.focusZone);
        this.on('pointerout', this.unfocusZone)
    }


    focusZone() {
        this.clear();
        this.beginFill(activeColor, 0.7);
        this.lineStyle(4, baseColor, 1);
        this.drawRect(0,0, width, height);
        this.endFill();

    }
    
    unfocusZone(){
        this.clear();
        this.beginFill(baseColor, 0.7);
        this.lineStyle(4, activeColor, 1);
        this.drawRect(0,0, width, height);
        this.endFill();

    }

}
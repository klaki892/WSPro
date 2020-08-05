import {ZoneName} from "./ZoneName";
import PlayZoneView from "../../view/field/PlayZoneView";

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


export default class PlayZone {
    readonly zoneName: ZoneName;
    view: PlayZoneView;


    constructor(zoneName: ZoneName) {
        this.zoneName = zoneName;
        this.view = new PlayZoneView(this);
    }

}
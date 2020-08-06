import {ZoneName} from "./ZoneName";
import PlayZoneView from "../../view/field/PlayZoneView";
import PlayCardView from "../../view/PlayCardView";
import Player from "../Player";

const baseColor = 0x000000;
const activeColor = 0xFFFFFF;
const width = 128;
const height = 170;
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
    owner: Player;

    private contents: PlayCardView[];

    constructor(zoneName: ZoneName, owner : Player) {
        this.zoneName = zoneName;
        this.owner = owner;
        this.view = new PlayZoneView(this);
        this.contents = [];
    }


    add(card: PlayCardView) {
        this.contents.push(card);
    }

    remove(card: PlayCardView){
        const index = this.contents.indexOf(card);
        if (index > -1) {
            this.contents.splice(index, 1);
        }
    }

    size() : Number{
        return this.contents.length;
    }

    getContents() : PlayCardView[] {
        return this.contents.slice();
    }


}
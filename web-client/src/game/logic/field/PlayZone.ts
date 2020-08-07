import {ZoneName} from "./ZoneName";
import PlayZoneView from "../../view/field/PlayZoneView";
import Player from "../Player";
import PlayCard from "../PlayCard";

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

    private contents: PlayCard[];

    constructor(zoneName: ZoneName, owner : Player) {
        this.zoneName = zoneName;
        this.owner = owner;
        this.view = new PlayZoneView(this);
        this.contents = [];
    }


    add(card: PlayCard) {
        this.contents.push(card);
    }

    set(card: PlayCard, index: number) {
        if (index >= this.contents.length){
            this.add(card);
        } else if (index < 0){
            this.contents.splice(0, 0, card);
        } else {
            this.contents.splice(index, 0, card);
        }
    }

    remove(card: PlayCard){
        const index = this.contents.indexOf(card);
        if (index > -1) {
            this.contents.splice(index, 1);
        }
    }

    size() : number{
        return this.contents.length;
    }

    getContents() : PlayCard[] {
        return this.contents.slice();
    }


}
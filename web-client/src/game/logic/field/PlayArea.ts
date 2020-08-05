import {ZoneName} from "./ZoneName";
import PlayZone from "./PlayZone";
import * as PIXI from "pixi.js";
import PlayAreaView from "../../view/field/PlayAreaView";


const {CLIMAX, MEMORY, CENTER_STAGE_LEFT, CENTER_STAGE_RIGHT, STOCK, BACK_STAGE_RIGHT, WAITING_ROOM, CENTER_STAGE_MIDDLE, LEVEL, HAND, DECK, CLOCK, BACK_STAGE_LEFT} = ZoneName;
export default class PlayArea extends PIXI.Container{

    private zonesMap: Map<ZoneName, PlayZone> = new  Map<ZoneName, PlayZone>();
    view: PlayAreaView

    constructor() {
        super();
        this.initZones();
        this.view = new PlayAreaView(this);
    }

    private initZones() {
        this.zonesMap.set(MEMORY, new PlayZone(MEMORY));
        this.zonesMap.set(DECK, new PlayZone(DECK));
        this.zonesMap.set(WAITING_ROOM, new PlayZone(WAITING_ROOM));
        this.zonesMap.set(CLIMAX, new PlayZone(CLIMAX));
        this.zonesMap.set(LEVEL, new PlayZone(LEVEL));
        this.zonesMap.set(HAND, new PlayZone(HAND));
        this.zonesMap.set(STOCK, new PlayZone(STOCK));
        this.zonesMap.set(CLOCK, new PlayZone(CLOCK));
        this.zonesMap.set(CENTER_STAGE_RIGHT, new PlayZone(CENTER_STAGE_RIGHT));
        this.zonesMap.set(CENTER_STAGE_MIDDLE, new PlayZone(CENTER_STAGE_MIDDLE));
        this.zonesMap.set(CENTER_STAGE_LEFT, new PlayZone(CENTER_STAGE_LEFT));
        this.zonesMap.set(BACK_STAGE_LEFT, new PlayZone(BACK_STAGE_LEFT));
        this.zonesMap.set(BACK_STAGE_RIGHT, new PlayZone(BACK_STAGE_RIGHT));
    }

    getZone(name: ZoneName): PlayZone{
        let zone = this.zonesMap.get(name);

        if (zone !== undefined){
            return zone;
        } else {
            console.error(name + " was undefined in the PlayArea.");
            // @ts-ignore - this should break the code if it happens.
            return undefined;
        }
    }


}
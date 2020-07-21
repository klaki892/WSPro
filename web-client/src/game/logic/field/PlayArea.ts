import {ZoneName} from "./ZoneName";
import PlayZone from "./PlayZone";
import * as PIXI from "pixi.js";


export default class PlayArea extends PIXI.Container{

    zones: Map<ZoneName, PlayZone>;

    constructor() {
        super();
        this.zones = new Map<ZoneName, PlayZone>();
        this.initZones();
    }

    private initZones() {

        let mem = new PlayZone(ZoneName.MEMORY);
        mem.position.set(800, 100)
        mem.pivot.set(mem.height/2, mem.width/2);
        mem.angle = 90;
        this.addChild(mem);

        let deckZone = new PlayZone(ZoneName.DECK);
        deckZone.position.set(700, 200)
        this.addChild(deckZone);

        let waitingRoom = new PlayZone(ZoneName.WAITING_ROOM);
        waitingRoom.position.set(700, 400)
        this.addChild(waitingRoom);

        let climax = new PlayZone(ZoneName.CLIMAX);
        climax.position.set(100, 200)
        climax.angle = 90;
        this.addChild(climax);


        //stage
        let csR = new PlayZone(ZoneName.CENTER_STAGE_RIGHT);
        csR.x = 500;
        this.addChild(csR);

        let csMid = new PlayZone(ZoneName.CENTER_STAGE_MIDDLE);
        csMid.x = 300;
        this.addChild(csMid);

        let csL =  new PlayZone(ZoneName.CENTER_STAGE_LEFT);
        csL.x = 100;
        this.addChild(csL);

        let bsL =  new PlayZone(ZoneName.BACK_STAGE_LEFT);
        bsL.position.set((csL.x+csMid.x)/2, 200)
        this.addChild(bsL);

        let bsR =  new PlayZone(ZoneName.BACK_STAGE_RIGHT);
        bsR.position.set((csMid.x+csR.x)/2, 200)
        this.addChild(bsR);

        // this.zones.set(CENTER_STAGE_MIDDLE, csMid);
        // this.zones.set(CENTER_STAGE_RIGHT, csR);
        // this.zones.set(CENTER_STAGE_LEFT, csL);

    }


}
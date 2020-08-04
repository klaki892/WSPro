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
        mem.position.set(900, 100)
        mem.pivot.set(mem.height/2, mem.width/2);
        mem.angle = 90;
        this.addChild(mem);

        let deckZone = new PlayZone(ZoneName.DECK);
        deckZone.position.set(800, 200)
        this.addChild(deckZone);
        this.zones.set(ZoneName.DECK, deckZone);

        let waitingRoom = new PlayZone(ZoneName.WAITING_ROOM);
        waitingRoom.position.set(800, 400)
        this.addChild(waitingRoom);

        let climax = new PlayZone(ZoneName.CLIMAX);
        climax.position.set(250, 200)
        climax.angle = 90;
        this.addChild(climax);

        //todo needs special stacking mechanism to see cards
        let level = new PlayZone(ZoneName.LEVEL);
        level.position.set(250, 400)
        level.angle = 90;
        this.addChild(level);

        //todo needs special stacking mechanism to see cards
        let hand = new PlayZone(ZoneName.HAND);
        hand.position.set(200, 600)
        this.addChild(hand);

        //todo needs special stacking mechanism to see cards
        let stock = new PlayZone(ZoneName.STOCK);
        stock.position.set(50, 200)
        stock.angle = 90;
        this.addChild(stock);

        //todo needs special stacking mechanism to see cards
        let clock =  new PlayZone(ZoneName.CLOCK);
        clock.position.set(300, 400)
        this.addChild(clock);



        //stage
        let csR = new PlayZone(ZoneName.CENTER_STAGE_RIGHT);
        csR.x = 600;
        this.addChild(csR);

        let csMid = new PlayZone(ZoneName.CENTER_STAGE_MIDDLE);
        csMid.x = 400;
        this.addChild(csMid);

        let csL =  new PlayZone(ZoneName.CENTER_STAGE_LEFT);
        csL.x = 200;
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
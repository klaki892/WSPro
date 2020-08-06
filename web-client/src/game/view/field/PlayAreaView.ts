import * as PIXI from "pixi.js";
import {ZoneName} from "../../logic/field/ZoneName";
import PlayArea from "../../logic/field/PlayArea";

export default class PlayAreaView extends PIXI.Container {
    private playArea: PlayArea;

    constructor(playArea: PlayArea) {
        super();
        this.playArea = playArea;

        this.initZones();
    }

    private initZones() {

        let memoryZone = this.playArea.getZone(ZoneName.MEMORY).view;
        memoryZone.position.set(407, 50)
        memoryZone.pivot.set(memoryZone.height/2, memoryZone.width/2);
        memoryZone.angle = 90;
        this.addChild(memoryZone);

        let deckZone = this.playArea.getZone(ZoneName.DECK).view;
        deckZone.position.set(440, 95)
        this.addChild(deckZone);

        let waitingRoom = this.playArea.getZone(ZoneName.WAITING_ROOM).view;
        waitingRoom.position.set(440, 190)
        this.addChild(waitingRoom);

        let climax = this.playArea.getZone(ZoneName.CLIMAX).view;
        climax.position.set(100, 100)
        climax.angle = 90;
        this.addChild(climax);

        //todo needs special stacking mechanism to see cards
        let level = this.playArea.getZone(ZoneName.LEVEL).view;
        level.position.set(100, 190)
        level.angle = 90;
        this.addChild(level);

        //todo needs special stacking mechanism to see cards
        let hand = this.playArea.getZone(ZoneName.HAND).view;
        hand.position.set(190, 285)
        this.addChild(hand);

        //todo needs special stacking mechanism to see cards
        let stock = this.playArea.getZone(ZoneName.STOCK).view;
        stock.position.set(0, 100)
        stock.angle = 90;
        this.addChild(stock);

        //todo needs special stacking mechanism to see cards
        let clock =  this.playArea.getZone(ZoneName.CLOCK).view;
        clock.position.set(190, 190)
        this.addChild(clock);



        //stage
        let csR = this.playArea.getZone(ZoneName.CENTER_STAGE_RIGHT).view;
        csR.x = 340;
        this.addChild(csR);

        let csMid = this.playArea.getZone(ZoneName.CENTER_STAGE_MIDDLE).view;
        csMid.x = 240;
        this.addChild(csMid);

        let csL =  this.playArea.getZone(ZoneName.CENTER_STAGE_LEFT).view;
        csL.x = 140;
        this.addChild(csL);

        let bsL =  this.playArea.getZone(ZoneName.BACK_STAGE_LEFT).view;
        bsL.position.set((csL.x+csMid.x)/2, 95)
        this.addChild(bsL);

        let bsR =  this.playArea.getZone(ZoneName.BACK_STAGE_RIGHT).view;
        bsR.position.set((csMid.x+csR.x)/2, 95)
        this.addChild(bsR);


        //customize the area itself after all declarations
        this.pivot.set(this.height/2, this.width/2);
    }
}
import * as PIXI from "pixi.js";

const baseColor = 0x000000;
const activeColor = 0xFFFFFF;
const width = 135;
const height = 158;


export default class PlayCard extends  PIXI.Sprite {
    frontImage: PIXI.Texture;
    backImage: PIXI.Texture;
    graphic: PIXI.Graphics;


    constructor(frontImage: PIXI.Texture, backImage: PIXI.Texture) {
        super();
        this.frontImage = frontImage;
        this.backImage = backImage;
        this.graphic = new PIXI.Graphics();
        this.addChild(this.graphic);
        this.drawCard();
        this.initCard();
    }

    private drawCard() {
        this.width = width;
        this.height = height;
        this.texture = this.backImage;
        // this.anchor.set(.5, .5);
        this.addChild(this.graphic);
        this.graphic.scale.set(1,1);
        console.log("Card Scale: " + this.scale.x + " " + this.scale.y);
    }

    flipCard(){
        this.texture = this.texture === this.frontImage
            ? this.backImage : this.frontImage;
        console.log("Card Scale: " + this.scale.x + " " + this.scale.y);

    }

    private initCard() {
        this.interactive = true;
        // this.interactiveChildren = true;
        this.on('pointerover', this.onCardHover)
        this.on('pointerout', this.onCardOut)
        this.on('pointertap', this.flipCard);

    }

    private onCardHover() {
        let newX = this.scale.x * 1.25;
        let newy = this.scale.y * 1.25;
        this.scale.set(newX, newy);
    }


    private onCardOut() {
        let oldX = this.scale.x / 1.25;
        let oldY = this.scale.y / 1.25;
        this.scale.set(oldX, oldY);
    }

}
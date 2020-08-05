import * as PIXI from "pixi.js";
import CardViewAnimations from "../view/CardViewAnimations";

const baseColor = 0x000000;
const activeColor = 0xFFFFFF;
const width = 135;
const height = 158;


export default class PlayCard extends  PIXI.Sprite {
    frontImage: PIXI.Texture;
    backImage: PIXI.Texture;
    graphic: PIXI.Graphics;

    static readonly baseWidth = width;
    static readonly baseHeight = height;


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
        this.anchor.set(.5);
        this.addChild(this.graphic);
        this.graphic.scale.set(1,1);
        console.log("Card Scale: " + this.scale.x + " " + this.scale.y);
    }

    flipCard(){

        let flipCardAnim = CardViewAnimations.flipCardAnim(this);
        this.interactive = false;
        flipCardAnim.timeScale(2)
        flipCardAnim.play();
        this.interactive = true;
    }

    private initCard() {
        this.interactive = true;
        // this.interactiveChildren = true;
        // this.on('pointerover', this.onCardHover)
        // this.on('pointerout', this.onCardOut)
        this.on('pointertap', this.flipCard);

    }

    private onCardHover() {
        let newX = this.scale.x * 1.25;
        let newy = this.scale.y * 1.25;
        this.scale.set(newX, newy);
        console.log(this.width +  " width & " + this.height + " height");
    }


    private onCardOut() {
        let oldX = this.scale.x / 1.25;
        let oldY = this.scale.y / 1.25;
        this.scale.set(oldX, oldY);
        console.log(this.width +  " width & " + this.height + " height");
    }

}
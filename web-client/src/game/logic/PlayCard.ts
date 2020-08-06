import PlayCardView from "../view/PlayCardView";
import PIXI from "pixi.js";

export default class PlayCard {
    view: PlayCardView

    constructor(frontImage: PIXI.Texture, backImage: PIXI.Texture) {
        this.view = new PlayCardView(frontImage, backImage)
    }

}
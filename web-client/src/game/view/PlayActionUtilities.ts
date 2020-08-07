import PlayCardView from "./PlayCardView";
import PlayZone from "../logic/field/PlayZone";
import CardViewAnimations from "./CardViewAnimations";
import Game from "../logic/Game";

export default class PlayActionUtilities {

    static moveCard(card: PlayCardView, toZone: PlayZone){

        let viewMovement = toZone.view.getNextCardPosition();

        //remove the card from the existing zone

        //add the card to the new zone

        //play animation

        //flip = previous visibility != incoming visibility
        CardViewAnimations.moveCardAnim(card, viewMovement, true).timeScale(1.5).play(0);

    }

    static DoAnimateAndOnComplete(animation: GSAPTimeline, callback: Function){
        animation.eventCallback('onComplete', callback());
        animation.play();
    }

    static getStageView(game : Game) : PIXI.Container {
        return game.view.getPixiApp().stage;
    }
}
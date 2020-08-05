import PlayCard from "../logic/PlayCard";
import {gsap} from "gsap";

export default class CardViewAnimations {

    static flipCardAnim(card: PlayCard) : GSAPTimeline{

        let nextFace = card.texture === card.frontImage
            ? card.backImage : card.frontImage;


        let t1 = gsap.timeline({
            autoRemoveChildren: true,
            paused: true

        });

        t1.to(card, {
            pixi: {
                width: 0,
                height: PlayCard.baseHeight * 1.1
            },
            duration: .2,
            onComplete: args => card.texture = nextFace

        })
            .to(card,{
                pixi: {
                    width: PlayCard.baseWidth,
                    height: PlayCard.baseHeight
                },
                duration: .2,
            } );

        return t1;

    }

}
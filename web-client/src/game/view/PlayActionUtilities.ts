import PlayCard from "../logic/PlayCard";
import PlayZone from "../logic/field/PlayZone";
import {gsap} from "gsap"

export default class PlayActionUtilities {

    static moveCard(card: PlayCard, toZone: PlayZone | undefined){
        if (toZone !== undefined) {
            gsap.to(card, {duration: 5, x: toZone.view.getGlobalPosition().x, y: toZone.view.getGlobalPosition().y});
        }
    }

}
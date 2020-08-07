import PlayCardView from "../view/PlayCardView";
import Game from "./Game";


export default class PlayCard {
    view: PlayCardView
    id: String
    guid!: string
    game: Game;
    constructor(id: string, game : Game) {
        this.id = id;
        this.game = game;
        this.view = new PlayCardView(this)
    }

}
import PlayArea from "./field/PlayArea";
import Game from "./Game";

export default class Player {
    name: string
    playArea!: PlayArea;
    game: Game

    constructor(name: string, game : Game) {
        this.name = name;
        this.game = game;

    }


}
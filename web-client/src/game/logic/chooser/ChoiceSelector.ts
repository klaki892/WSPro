import Game from "../Game";
import ChoiceSelectorView from "../../view/ChoiceSelectorView";
import Choice from "./Choice";


export default class ChoiceSelector {
    private game : Game
    private view : ChoiceSelectorView
    private options : Choice[];

    constructor(game : Game, options : Choice[]) {
        this.game = game;
        this.options = options;
        this.view = new ChoiceSelectorView(this);
    }
}
import Game from "../Game";

export default interface GameAction {

    isExecuting: boolean

    perform(game: Game, onComplete: Function) : void
}
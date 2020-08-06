/**
 * Responsible for managing GameState and knowledge of objects
 */
import Player from "./Player";
import GameView from "../view/GameView";
import GameInfo from "../GameInfo";
import PlayArea from "./field/PlayArea";

export default class Game {

    private gameInfo: GameInfo;
    view: GameView;
    players: Player[] = [];
    localPlayer!: Player;
    oppPlayer!: Player;

    constructor(gameInfo : GameInfo) {
        this.gameInfo = gameInfo;
        this.view = new GameView(this);
    }

    /**
     * Sets up the game, then begins playing it.
     */
    init(){
        //todo connect to network, setup handler for events

        //setup players
        this.localPlayer = new Player(this.gameInfo.playerName, this);
        this.oppPlayer = new Player(this.gameInfo.opponentName, this);
        this.players.push(this.localPlayer,this.oppPlayer);

        //setup areas
        for (let player of this.players) {
            player.playArea = new PlayArea(player);
        }


        //init playing field drawing
        this.view.init();

        //start execution of the game
        this.startGame();
    }

    startGame(){
        this.view.startRender();
    }

    /**
     * Stops the Game, killing UI, closing connections and doing cleanup
     */
    endGame(){
        //todo close network

        //stop UI
        this.view.stopRender();
    }

}
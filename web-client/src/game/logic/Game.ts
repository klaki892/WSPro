/*******************************************************************************
 *  WSPro - Trading Card Game Simulator
 *  Copyright (C) 2020  Klayton Killough
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published
 *  by the Free Software Foundation, version 3 of the License.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 ******************************************************************************/

/**
 * Responsible for managing GameState and knowledge of objects
 */
import Player from "./Player";
import GameView from "../view/GameView";
import GameInfo from "../GameInfo";
import PlayArea from "./field/PlayArea";
import GameAction from "./actions/GameAction";
import GrpcNetworkManager from "../grpc/GrpcNetworkManager";
import GameState from "./GameState";

export default class Game {

    private gameInfo: GameInfo;
    view: GameView;
    players: Player[] = [];
    localPlayer!: Player;
    oppPlayer!: Player;
    actionQueue: GameAction[] = [];
    networkManager! : GrpcNetworkManager;
    gameState: GameState = new GameState();
    private executingAction: boolean = false;

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
        //connect to game and mark as ready
        this.view.startRender();
        this.networkManager = new GrpcNetworkManager(this, this.gameInfo);
    }

    private executeAction() {
        //if we have an action, execute
            if (!this.executingAction) {

                let action = this.actionQueue[0];
                if (action !== undefined) {
                    this.executingAction = true;
                    this.actionQueue = this.actionQueue.splice(0,0);
                    //perform action
                    action.perform(this, () => {
                        this.executingAction = false;
                        this.executeAction(); //start next loop or action.
                    });

                }
            }
    }

    //when an action is recieved, perform a game loop
    // this is to prevent the game from not continuing if the action queue ever empties
    notifyActionReceived() {

        if (this.actionQueue.length === 1) {
            this.executeAction();
        }

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
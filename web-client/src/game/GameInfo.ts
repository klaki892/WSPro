/**
 * Holds all meta information pertaining to a paticular game
 */
export default class GameInfo {

    playerName!: string;
    gameToken: string;
    serverURL!: string


    opponentName: string = "Opponent"

    constructor(playerName : string, gameToken: string, serverURL: string) {
        this.playerName = playerName;
        this.gameToken = gameToken;
        this.serverURL = serverURL;
    }


}
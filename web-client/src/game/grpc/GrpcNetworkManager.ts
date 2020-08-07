import Game from "../logic/Game";
import GameInfo from "../GameInfo";
import {PlayWeissServiceClient} from "./generated/playGame_pb_service";
import {GrpcGameConnectRequest, GrpcPlayerToken} from "./generated/playGame_pb";
import {GameMessageProto} from "./generated/gameMessages_pb";
import {GameTriggerProto, ProtoCardMovedTrigger} from "./generated/ProtoEntities_pb";
import {PlayRequestProto} from "./generated/PlayRequest_pb";
import CardMoveAction from "../logic/actions/CardMoveAction";

export default class GrpcNetworkManager {
    private game: Game;
    private connectionInfo: GameInfo;

    private client!: PlayWeissServiceClient;
    private playerToken!: GrpcPlayerToken;

    connectionEstablished: boolean = false;

    constructor(game : Game, connectionInfo: GameInfo) {
        this.game = game;
        this.connectionInfo = connectionInfo;
        this.initConnection();
    }

    //connects to the game server, retrieves the token for acting as the player.
    private initConnection() {
        this.client = new PlayWeissServiceClient(this.connectionInfo.serverURL);
        let connectRequest = new GrpcGameConnectRequest();
        connectRequest.setPlayername(this.connectionInfo.playerName);
        connectRequest.setGameidentifier(this.connectionInfo.gameToken);

        this.client.connectToGame(connectRequest, (err, response) => {
            if (response?.getConnectionaccepted()){ //successful, we have a token and can play the game
                if (response?.hasToken()){
                    this.playerToken  = response?.getToken() as GrpcPlayerToken;
                    this.connectionEstablished = true;
                    console.log("Connected to Server " + this.connectionInfo.serverURL +
                        " as " + this.connectionInfo.playerName)
                    this.readyUp()

                } else {

                    //todo report couldnt connect
                    let msg = "Connected but no token";
                    console.error(msg)
                    console.error(err)
                    console.error(response)
                    throw new Error(msg);

                }
            } else { //no game - throw error
                //todo report couldnt connect, stop game creation
                let msg = "Error Connecting to game.";
                console.error(msg);
                console.error(response);
                console.error(err);
                throw new Error(msg);
            }
        });
    }

    readyUp(){
        if (this.connectionEstablished){
            this.client.readyUp(this.playerToken, (error, responseMessage) => {
                if (responseMessage !== null) {
                    if (responseMessage.getWassuccessful()) {
                        console.log("Network: Readied Up!")


                        //handle all events we missed since the game began.
                        //todo catch-up events from user should not be visible to the user while they occur
                        this.client.getAllPastGameEvents(this.playerToken)
                            .on("data", (msg) => this.onGrpcGameEvent(msg))
                            .on("end", () => {
                                console.log("Finished Catching up to past events");
                                //start live listening connection and handle game events
                                this.client.listenToGameEvents(this.playerToken).on("data", (msg) => this.onGrpcGameEvent(msg))
                            });


                    } else {
                        console.log("Failed to ready up")
                        this.connectionEstablished = false;
                    }
                }
            });
        } else {
            this.connectionEstablished = false;
            console.error("Couldn't Ready Up - no connection to game server")
        }
    }

    private onGrpcGameEvent(message : GameMessageProto){
        console.log(message.toString());
        if (message.hasTrigger()){
            let trigger = message.getTrigger() as GameTriggerProto;
            console.log("trigger")
            this.handleTrigger(trigger);
        } else if (message.hasRequest()) {
            let request = message.getRequest() as PlayRequestProto;
            this.handlePlayRequest(request);
            console.log("got request");
        } else {
            //todo throw game ending exception
            console.error("Received unknown game message");
            console.error(message);
        }
    }

    private handleTrigger(trigger: GameTriggerProto) {
        //todo handle all triggers, turn them into actions
        let triggertypeCase = trigger.getTriggertypeCase();
        console.log("triggerCase: " + triggertypeCase)
        // let caseToNumber = triggertypeCase.valueOf();
        switch (triggertypeCase) {
            case GameTriggerProto.TriggertypeCase.CARDMOVEDTRIGGER: //Card moved trigger - equivalent ENUM number, cant use actual enum due to typescripting errors
                console.log("card moved");
                this.game.actionQueue.push(new CardMoveAction(trigger.getCardmovedtrigger() as ProtoCardMovedTrigger));
                this.game.notifyActionReceived();
                break;
            case GameTriggerProto.TriggertypeCase.ZONESHUFFLEDTRIGGER:
                console.log("zone shuffled")
                break;
            default:
                console.log("something else happened");

        }
    }

    private handlePlayRequest(request: PlayRequestProto) {
        //todo
        console.log("got request");
    }
}
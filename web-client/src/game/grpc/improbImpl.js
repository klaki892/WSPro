import {PlayWeissServiceClient} from "./generated/playGame_pb_service";
import {GrpcGameConnectRequest} from "./generated/playGame_pb";
import React from "react";


class FirstComponent extends React.Component{

    componentDidMount() {
        makeCall();
    }

    render() {
        return (
            <div>
                Showing something
            </div>
        );
    }
}
function makeCall() {


    const grpcURL = "http://" + window.location.hostname + ":80";

// improbRPC.build(messages, services, grpcURL)

    let client = new PlayWeissServiceClient(grpcURL);

    let connectREquest = new GrpcGameConnectRequest();

    let playerToken;

    connectREquest.setPlayername("p2");
    connectREquest.setGameidentifier("fe685056-e90c-3f9b-8a07-e5863e4636dc");

    client.connectToGame(connectREquest, (err, token) => {
        console.log(token?.getConnectionaccepted())
        console.log(token?.getToken().getToken())
        client.readyUp(token.getToken(), (error, responseMessage) => {
            console.log(responseMessage.getWassuccessful());
            console.log("ready to listen")
            // var types = GameTriggerProto.TriggertypeCase;

            client.listenToGameEvents(token.getToken()).on("data", message => {
                console.log(message.toString());
                if (message.hasTrigger()){
                    let trigger = message.getTrigger();
                    console.log("trigger")

                    let triggertypeCase = trigger.getTriggertypeCase();
                    console.log("triggerCase: " + triggertypeCase)
                    // let caseToNumber = triggertypeCase.valueOf();
                    switch (triggertypeCase) {
                        case 5: //Card moved trigger - equivalent ENUM number, cant use actual enum due to typescripting errors
                            console.log("card moved");
                            break;
                        case 22:
                            console.log("zone shuffled")
                            break;
                        default:
                            console.log("something else happened");

                    }
                } else {
                    console.log("got request");
                }
            })

        })
    })
}



//declare class here and the wrapper will go around the class definition
export default FirstComponent;
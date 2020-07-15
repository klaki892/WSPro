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

    let request = new GrpcGameConnectRequest();

    request.setPlayername("p2");
    request.setGameidentifier("4c4c6eb9-6280-3788-a77c-2e21efff3a4b");

    client.connectToGame(request, (err, response) => {
        console.log(response?.getConnectionaccepted())
        console.log(response?.getToken())
    })
}



//declare class here and the wrapper will go around the class definition
export default FirstComponent;
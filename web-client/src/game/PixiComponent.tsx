import * as React from 'react';
import * as PIXI from "pixi.js";
import Game from "./logic/Game";
import GameInfo from "./GameInfo";

const WIDTH = 1920;
const HEIGHT = 1080;

export class PixiComponent extends React.Component {
    app: PIXI.Application;
    gameCanvas: HTMLDivElement | any;
    private game: Game;

    constructor({props, context}: { props: any, context: any }) {
        super(props, context);

        //todo retrieve game properties from actual global source
        let playerName = "p1";
        let gameToken = "token";
        let url = "https://server.url";

        let gameInfo = new GameInfo(playerName, gameToken, url);
        //create new game
        this.game = new Game(gameInfo);

        this.app = this.game.view.getPixiApp();
    }

    /**
     * After mounting, add the PIXI Renderer to the div and start the Application.
     */
    componentDidMount() {

        this.gameCanvas.appendChild(this.app.view);

        //component loaded, start loading game.
        this.game.init()
    }


    /**
     * Stop the Application when unmounting.
     */
    componentWillUnmount() {
        this.game.endGame();
    }

    /**
     * Simply render the div that will contain the PIXI Renderer.
     */
    render() {
        let component = this;
        return (
            <React.Fragment>
            <div ref={(thisDiv) => {component.gameCanvas = thisDiv}} />
            {/*<img src={cardBack}/>*/}
            </React.Fragment>
        );
    }
}

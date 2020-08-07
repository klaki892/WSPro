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

import * as React from 'react';
import * as PIXI from "pixi.js";
import Game from "./logic/Game";
import GameInfo from "./GameInfo";

export class PixiComponent extends React.Component {
    app: PIXI.Application;
    gameCanvas: HTMLDivElement | any;
    private game: Game;

    constructor({props, context}: { props: any, context: any }) {
        super(props, context);

        //todo retrieve game properties from actual global source
        let playerName = "p2";
        let gameToken = "ff2b9868-d419-34c6-8495-d52e672bfe73";
        let url = "http://localhost:80";

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

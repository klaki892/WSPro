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

import Game from "../logic/Game";
import * as PIXI from "pixi.js";
import {gsap} from "gsap";
import {PixiPlugin} from "gsap/PixiPlugin";
import cardBack from "../../resources/cardBack.png";
import cardFront from "../../resources/cardFront.png";
import PlayCardView from "./PlayCardView";
import {ZoneName} from "../logic/field/ZoneName";
import PlayActionUtilities from "./PlayActionUtilities";
import PlayCard from "../logic/PlayCard";


//pixi settings
const WIDTH = 1024;
const HEIGHT = 768;


export default class GameView {
    private game: Game;
    private pixiApp: PIXI.Application;

    constructor(game: Game) {
        this.game = game;
        this.pixiApp = new PIXI.Application({
            width: WIDTH,
            height: HEIGHT,
            antialias: true,
            transparent: false,
            resolution: 1,
            autoStart: false
        });
    }

    init(){

        //register GSAP for animation handling
        gsap.registerPlugin(PixiPlugin);
        PixiPlugin.registerPIXI(PIXI);
        this.pixiApp.renderer.backgroundColor = 0xff;

        //auto resizing
        //FIXME: resize doesnt happen on game start, everyone starts with 1920x1080. this shouldnt happen.
        // this.autoResize(this.app)(); //this resizes, but destorys movement
        window.addEventListener('resize', this.autoResize(this.pixiApp));

        //todo load actual assets
        this.pixiApp.loader.add("testCardBack", cardBack)
            .add("testCardFront", cardFront)
            .load(() => this.setupStage(this.pixiApp))



    }

    private setupStage(app: PIXI.Application){


        //todo only setup, dont do any actions.
        let sprite = new PlayCardView( new PlayCard("testCard", this.game))
        // let sprite = PIXI.Sprite.from('https://i.imgur.com/rRoIHdc.png');
        sprite.x = 100;
        sprite.y = 100;


        let oppStageArea = this.game.oppPlayer.playArea;
        let oppStageAreaView = oppStageArea.view;
        oppStageAreaView.x = 775;
        oppStageAreaView.y = 65;
        oppStageAreaView.angle = 180;

        let playerStageArea = this.game.localPlayer.playArea;
        let playerStageAreaView = playerStageArea.view;
        playerStageAreaView.x = 670;
        playerStageAreaView.y = 700;
        app.stage.addChild(oppStageAreaView);
        app.stage.addChild(playerStageAreaView);

        let card2 = new PlayCardView( new PlayCard("testCard", this.game))
        // card2.flipCard();

        app.stage.addChild(card2);

        let deckZone = playerStageArea.getZone(ZoneName.DECK).view;
        if (deckZone !== undefined){
            card2.position = deckZone.getGlobalPosition();
        }

        app.stage.addChild(sprite);
        let message = new PIXI.Text("Text");
        app.stage.addChild(message);
        message.position.set(sprite.x, sprite.y);

        PlayActionUtilities.moveCard(card2, playerStageArea.getZone(ZoneName.LEVEL));
        PlayActionUtilities.moveCard(sprite, oppStageArea.getZone(ZoneName.LEVEL));


        app.ticker.add((delta: any) =>{
            // sprite.y += 1;
            message.position = sprite.position;
        //     // console.log(app)
        //     // this.autoResize(app);
        })

        /*Todo:
        * Add Special zones for Stackable cards? (hand and stock)
         */
    }


    private autoResize(app: PIXI.Application) {
        return function() {
            // console.log(this.app)
            if (app !== undefined) {
                console.log("resize hit")
                const vpw = window.innerWidth;  // Width of the viewport
                const vph = window.innerHeight; // Height of the viewport
                let nvw; // New game width
                let nvh; // New game height

                // The aspect ratio is the ratio of the screen's sizes in different dimensions.
                // The height-to-width aspect ratio of the game is HEIGHT / WIDTH.

                if (vph / vpw < HEIGHT / WIDTH) {
                    // If height-to-width ratio of the viewport is less than the height-to-width ratio
                    // of the game, then the height will be equal to the height of the viewport, and
                    // the width will be scaled.
                    nvh = vph;
                    nvw = (nvh * WIDTH) / HEIGHT;
                } else {
                    // In the else case, the opposite is happening.
                    nvw = vpw;
                    nvh = (nvw * HEIGHT) / WIDTH;
                }

                // Set the game screen size to the new values.
                // This command only makes the screen bigger --- it does not scale the contents of the game.
                // There will be a lot of extra room --- or missing room --- if we don't scale the stage.
                app.renderer.resize(nvw, nvh);

                // This command scales the stage to fit the new size of the game.
                app.stage.scale.set(nvw / WIDTH, nvh / HEIGHT);


            }
        }
    }


    startRender(){
        this.pixiApp.start();
    }

    getPixiApp() : PIXI.Application{
        return this.pixiApp;
    }

    stopRender() {
        this.pixiApp.stop();
    }
}
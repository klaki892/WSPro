import * as React from 'react';
import * as PIXI from "pixi.js";
import cardBack from '../resources/cardBack.png';
import PlayArea from "./logic/field/PlayArea";

export class PixiComponent extends React.Component {
    app: PIXI.Application | any;
    gameCanvas: HTMLDivElement | any;

    constructor({props, context}: { props: any, context: any }) {
        super(props, context);

    }

    /**
     * After mounting, add the PIXI Renderer to the div and start the Application.
     */
    componentDidMount() {
        this.app = new PIXI.Application({
            width: window.innerWidth,
            height: window.innerHeight,
            antialias: true,
            transparent: false,
            resolution: 1,
            forceCanvas: true
        });
        this.gameCanvas.appendChild(this.app.view);

        this.app.renderer.backgroundColor = 0xff;
        this.app.renderer.autoResize = true;

        this.app.loader.add("cardBack", cardBack).load(() => this.setup(this.app))
        // this.setup(this.app)

        this.app.start();
    }

    setup(app: PIXI.Application){
        let sprite = new PIXI.Sprite(
            app.loader.resources["cardBack"].texture
        )
        // let sprite = PIXI.Sprite.from('https://i.imgur.com/rRoIHdc.png');
       sprite.x = 100;
       sprite.y = 100;
       sprite.width = 128;
       sprite.height = 128;
       sprite.anchor.set(.5, .5);
       sprite.rotation = .5;

       // let rectangle = new PIXI.Graphics();
       // rectangle.beginFill(0x66CCFF);
       // rectangle.lineStyle(4, 0xFF3300, 1);
       // rectangle.drawRect(0, 0, 64, 64);
       //  let centerStageMiddle = new PlayZone(ZoneName.CENTER_STAGE_MIDDLE);
       // centerStageMiddle.x = 200;
       // centerStageMiddle.y = 100;
       // app.stage.addChild(centerStageMiddle);
        let playerStageArea = new PlayArea();
        playerStageArea.x = 200;
        app.stage.addChild(playerStageArea);

       app.stage.addChild(sprite);
       let message = new PIXI.Text("Hello World");
       app.stage.addChild(message);
       message.position.set(sprite.x, sprite.y);
       this.app.ticker.add((delta: any) =>{
           sprite.y += 1;
           message.position = sprite.position;
       })

        /*Todo:
        *  Make classes for cards and playzones
        * Add Special zones for Stackable cards? (hand and stock)
        * Create function to move a card from zone to zone
        * Look into Hexi and other GUI based libraries for fast implementing buttons and other technology
        * (We Might have to downgrade to get capability. */
    }

    /**
     * Stop the Application when unmounting.
     */
    componentWillUnmount() {
        this.app.stop();
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
